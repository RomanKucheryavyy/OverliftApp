const functions = require('firebase-functions');

var { google } = require('googleapis')
var MESSASGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging"
var SCOPES = [MESSASGING_SCOPE, "https://www.googleapis.com/auth/cloud-platform", "https://www.googleapis.com/auth/firebase.database", "https://www.googleapis.com/auth/userinfo.email"]

var express = require('express');

var bodyParser = require('body-parser');
var router = express.Router();

router.use(bodyParser.json());
router.use(bodyParser.urlencoded({ extended: true }));


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
        });

    }, function (error, response, body) {
        res.end(body);
        console.log(body);

    });
});


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

exports.api = functions.https.onRequest(router);
// module.exports = router;
