const functions = require('firebase-functions');

// The Firebase Admin SDK to access the Firebase Realtime Database.
// var admin = require('firebase-admin');

// var refreshToken; // Get refresh token from OAuth2 flow

// admin.initializeApp({
//   credential: admin.credential.refreshToken(refreshToken),
//   databaseURL: 'https://overliftapp.firebaseio.com'
// });


var { google } = require('googleapis')
var MESSASGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging"
var SCOPES = [MESSASGING_SCOPE, "https://www.googleapis.com/auth/cloud-platform", "https://www.googleapis.com/auth/firebase.database", "https://www.googleapis.com/auth/userinfo.email"]

var express = require('express');
var app = express();
var apps = express();


var bodyParser = require('body-parser');
var router = express.Router();

var request = require('request');

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

router.post('/send', function (req, res) {

    getAccessToken().then(function (access_token) {

        var title = req.body.title;
        var body = req.body.body;
        var token = req.body.token;

        request.post({
            headers: {
                Authorization: 'Bearer ' + access_token
            },
            url: "https://fcm.googleapis.com/v1/projects/overliftapp/messages:send",
            body: JSON.stringify(
                {
                    "message": {
                        "token": token,
                        "notification": {
                            "body": body,
                            "title": title
                        }
                    }
                }
            )

        }, function (error, response, body) {
            res.end(body);
            console.log(body);

        });

    });

    // res.json({
    //     'message' : 'Hurray! It is working'
    // });

});

app.use('/api', router);

function getAccessToken() {
    return new Promise(function (resolve, reject) {
        var key = require("./service-account-file.json");
        var jwtClient = new google.auth.JWT(
            key.client_email,
            null,
            key.private_key,
            SCOPES,
            null
        );
        jwtClient.authorize(function (err, tokens) {
            if (err) {
                reject(err);
                return;
            }
            resolve(tokens.access_token)
        });
    });
}

apps.get('/', (req, res) => {
    const date = new Date();
    const hours = (date.getHours() % 12) + 1;  // London is UTC + 1hr;
    res.send(`
      <!doctype html>
      <head>
        <title>Time</title>
        <link rel="stylesheet" href="/style.css">
        <script src="/script.js"></script>
      </head>
      <body>
        <p>In London, the clock strikes:
          <span id="bongs">${'BONG '.repeat(hours)}</span></p>
        <button onClick="refresh(this)">Refresh</button>
      </body>
    </html>`);
});

apps.get('/api', (req, res) => {
    const date = new Date();
    const hours = (date.getHours() % 12) + 1;  // London is UTC + 1hr;
    res.json({ bongs: 'BONG '.repeat(hours) });
});

exports.app = functions.https.onRequest(app);
exports.apps = functions.https.onRequest(apps);


exports.bigben = functions.https.onRequest((req, res) => {
    const hours = (new Date().getHours() % 12) + 1  // London is UTC + 1hr;
    res.status(200).send(`<!doctype html>
      <head>
        <title>Time</title>
      </head>
      <body>
        ${'BONG '.repeat(hours)}
      </body>
    </html>`);
});

