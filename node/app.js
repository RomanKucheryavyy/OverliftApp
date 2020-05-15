var {google} = require('googleapis')
var MESSASGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging"
var SCOPES = [MESSASGING_SCOPE]

// var http = require('http')
var express = require('express');
var app = express();

var bodyParser = require('body-parser');
var router = express.Router();

var port = 8080;

var request = require('request');

app.use(bodyParser.urlencoded({extended:true}));
app.use(bodyParser.json());

router.post('/send', function(req, res){

    getAccessToken().then(function(access_token){

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
                    "message":{
                        "token" : token,
                        "notification" : {
                            "body" : "This is an FCM notification message:",
                            "title" : "FCM Message"
                        }
                    }
                }
            )

        }, function(error, response, body){
            res.end(body);
            console.log(body);

        });

    });

    // res.json({
    //     'message' : 'Hurray! It is working'
    // });

});

app.use('/api', router);

app.listen(port, function(){
    console.log("Server is listening to port " + port);
});

function getAccessToken() {
    return new Promise(function(resolve, reject) {
        var key = require("./service-account.json");
        var jwtClient = new google.auth.JWT(
            key.client_email,
            null,
            key.private_key,
            SCOPES,
            null
        );
        jwtClient.authorize(function(err, tokens) {
            if(err) {
                reject(err);
                return;
            }
            resolve(tokens.access_token)
        });
    });
}

