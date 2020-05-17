/**
 * Overlift App
 * Ross, Roman, Ilya, Mercedes
 * Group 2
 * TCSS 450
 */

package edu.tacoma.uw.mtchea.overliftapp;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Base class for receiving messages from Firebase Cloud Messaging.
 * Helps to automatically display messages/notifications.
 * @author Ilya Bokov
 * @version May 14, 2020
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    /**
     * What happens when a message is received.
     * @param remoteMessage the message that came in.
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            NotificationHelper.displayNotification(getApplicationContext(), title, body);
        }

    }
}
