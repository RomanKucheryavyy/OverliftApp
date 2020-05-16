package edu.tacoma.uw.mtchea.overliftapp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * Helps the notifications to be built using the NotificationCompact.
 * @author Ilya Bokov
 * @version May 15, 2020
 */
public class NotificationHelper {

    /**
     * Displays the notification
     * @param context the context.
     * @param title title of the notification
     * @param body body of the notification
     */
    public static void displayNotification(Context context, String title, String body) {

        Intent intent = new Intent(context, SocialActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                100,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );




        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, SocialNotification.CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_message)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, mBuilder.build());
    }
}
