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

'use strict';

const functions = require('firebase-functions');
const admin = require('firebase-admin');
const md5 = require('md5')

const { Logging } = require('@google-cloud/logging');
const logging = new Logging();
const log = logging.log('my-custom-log-name');

admin.initializeApp();

const Sound = "default";
const iOSAction = "co.chatsdk.QuickReply";
const blockedUsersEnabled = true;
const loggingEnabled = true;
const reciprocalContactsEnabled = false;

const enableV4Compatibility = true;

function buildContactPushMessage (title, theBody, clickAction, sound, senderId, recipientId) {

    let data = {
        chat_sdk_contact_entity_id: senderId,
    };

    return buildMessage(title, theBody, clickAction, sound, data, recipientId);

}

function buildMessagePushMessage (title, theBody, clickAction, sound, type, senderId, threadId, recipientId) {

    // Make the token payload
    let body = theBody;

    let messageType = parseInt(type);
    if (messageType === 1) {
        body = "Location";
    }
    if (messageType === 2) {
        body = "Image";
    }
    if (messageType === 3) {
        body = "Audio";
    }
    if (messageType === 4) {
        body = "Video";
    }
    if (messageType === 5) {
        body = "System";
    }
    if (messageType === 6) {
        body = "Sticker";
    }
    // if (messageType === 7) {
    //     body = "File";
    // }

    let data = {
        chat_sdk_thread_entity_id: threadId,
        chat_sdk_user_entity_id: senderId,
        chat_sdk_push_title: title,
        chat_sdk_push_body: body,
    };

    return buildMessage(title, body, clickAction, sound, data, recipientId);

}

function buildMessage (title, body, clickAction, sound, data, recipientId) {

    if (enableV4Compatibility) {
        // Make the user ID safe
        recipientId = recipientId.split(".").join("1");
        recipientId = recipientId.split("%2E").join("1");
        recipientId = recipientId.split("@").join("2");
        recipientId = recipientId.split("%40").join("2");
        recipientId = recipientId.split(":").join("3");
        recipientId = recipientId.split("%3A").join("3");
    } else {
        recipientId = md5(recipientId);
    }

    return {
        data: data,
        apns: {
            headers: {
            },
            payload: {
                aps: {
                    alert: {
                        title: title,
                        body: body
                    },
                    badge: 1,
                    sound: sound,
                    priority: "high",
                    category: clickAction,
                }
            },
        },
        topic: recipientId,
    };
}

function logData (data) {
    if (loggingEnabled) {
        const entry = log.entry({}, data);
        log.write(entry);
    }
}

function getUserName(usersRef, userId) {
    return getUserMeta(usersRef, userId).then(meta => {
        return getUserNameFromMeta(meta);
    });
}

function getUserIds (threadRef, senderId) {
    return threadRef.child('users').once('value').then((users) => {

        let IDs = [];

        let usersVal = users.val();
        if (usersVal !== null) {
            for (let userID in usersVal) {
                if (usersVal.hasOwnProperty(userID)) {
                    logData("UsersVal:" + usersVal);
                    let muted = usersVal[userID]["mute"];
                    logData("Muted:" + muted);
                    if (userID !== senderId && muted !== true) {
                        IDs.push(userID);
                    } else {
                        logData("Pushes muted for:" + userID);
                    }
                }
            }
        }

        return IDs;
    });
}

function unORNull(object) {
    return object === 'undefined' || object === null || !object;
}

function getUserMeta (usersRef, userId) {
    return usersRef.child(userId).child('meta').once('value').then((meta) => {
        let metaValue = meta.val();
        if (metaValue !== null) {
            return metaValue;
        }
        return null;
    });
}

function isBlocked (usersRef, recipientId, testId) {
    if (blockedUsersEnabled) {
        return usersRef.child(recipientId).child('blocked').child(testId).once('value').then((blocked) => {
            let blockedValue = blocked.val();
            logData("recipient: " + recipientId + ", testId: "+ testId + "val: " + JSON.stringify(blockedValue) + ", send push: " + blockedValue === null);
            return blockedValue !== null;
        });
    } else {
        return Promise.resolve(false);
    }
}

function getBlockedUsers (usersRef, userId) {
    if (blockedUsersEnabled) {
        return usersRef.child(userId).child('blocked').once('value').then((blocked) => {
            let blockedValue = blocked.val();

            // logData("usersRef " + usersRef.toString() + " uid: " + userId + ", val: " + blockedValue);

            if (blockedValue !== null) {
                return blockedValue;
            }
            return null;
        });
    } else {
        return Promise.resolve(null);
    }
}

function getUserNameFromMeta (metaValue) {
    if (metaValue !== null) {
        let name = metaValue["name"];
        if(unORNull(name)) {
            name = "Unknown";
        }
        return name;
    }
    return null;
}

exports.pushToChannels = functions.https.onCall((data, context) => {

    let body = data.body;

    let action = data.action;
    if(!action) {
        action = iOSAction;
    }

    let sound = data.sound;
    if(!sound) {
        sound = Sound;
    }

    let type = data.type;
    let senderId = String(data.senderId);
    let threadId = String(data.threadId);

    let userIds = data.userIds;

    if(unORNull(senderId)) {
        throw new functions.https.HttpsError("invalid-argument", "Sender ID not valid");
    }

    if(unORNull(threadId)) {
        throw new functions.https.HttpsError("invalid-argument", "Sender ID not valid");
    }

    if(unORNull(body)) {
        throw new functions.https.HttpsError("invalid-argument", "Sender ID not valid");
    }

    var status = {};
    for(let uid in userIds) {
        if(userIds.hasOwnProperty(uid)) {
            let userName = userIds[uid];
            let message = buildMessage(userName, body, action, sound, type, senderId, threadId, uid);
            status[uid] = message;
            admin.messaging().send(message);
        }
    }
    // return Promise.all(promises);
    // return res.send(status);
    return status;

});

if (reciprocalContactsEnabled) {
    exports.contactListener = functions.database.ref('{rootPath}/users/{userId}/contacts/{contactId}').onCreate((contactSnapshot, context) => {

        const contactVal = contactSnapshot.val();
        const userId = context.params.userId;
        const contactId = context.params.contactId;
        const rootPath = context.params.rootPath;

        if (!unORNull(userId) && !unORNull(contactId)) {

            const ref = admin.database().ref(rootPath).child("users").child(contactId).child("contacts").child(userId);
            const usersRef = admin.database().ref(rootPath).child("users");

            return ref.set({type: 0}).then(() => {
                return getUserName(usersRef, userId).then(name => {
                    let message = buildContactPushMessage(name, "Added you as a contact", iOSAction, Sound, userId, contactId);
                    return admin.messaging().send(message).then(success => {
                        return success;
                    }).catch(error => {
                        console.log(error.message);
                    });
                });
            });
        }

    });
}

exports.pushListener = functions.database.ref('{rootPath}/threads/{threadId}/messages/{messageId}').onCreate((messageSnapshot, context) => {

    let messageValue = messageSnapshot.val();

    let senderId = messageValue["from"];

    if (enableV4Compatibility && unORNull(senderId)) {
        senderId = messageValue["user-firebase-id"];
    }

    if(unORNull(senderId)) {
        return 0;
    }

    let threadId = context.params.threadId;
    let rootPath = context.params.rootPath;

    let threadRef = admin.database().ref(rootPath).child("threads").child(threadId);
    let usersRef = admin.database().ref(rootPath).child("users");

    return getUserMeta(usersRef, senderId).then(meta => {
        return getUserIds(threadRef, senderId).then(IDs => {

            const name = getUserNameFromMeta(meta);

            const promises = [];
            for(let i in IDs) {
                let userId = IDs[i];
                promises.push(isBlocked(usersRef, userId, senderId).then(isBlocked => {
                    if (!isBlocked) {
                        let messageText;

                        let meta = messageValue["meta"];
                        if (!unORNull(meta)) {
                            messageText = meta["text"];
                        }

                        if (enableV4Compatibility && unORNull(messageText)) {
                            meta = messageValue["json_v2"];
                            if (!unORNull(meta)) {
                                messageText = meta["text"];
                            }
                        }

                        if (!unORNull(messageText)) {
                            let message = buildMessagePushMessage(name, messageText, iOSAction, Sound, messageValue['type'], senderId, threadId, userId);
                            logData("Send push: " + messageText + ", to: " + userId);
                            admin.messaging().send(message).then(success => {
                                return success;
                            }).catch(error => {
                                console.log(error.message);
                            });
                        }
                    } else {
                        logData("push blocked to: " + userId);
                    }
                    return 0;
                }));
            }
            return Promise.all(promises);
        });
    });

});
