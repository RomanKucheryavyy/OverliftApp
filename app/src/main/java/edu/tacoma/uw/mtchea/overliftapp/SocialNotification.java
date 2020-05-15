package edu.tacoma.uw.mtchea.overliftapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;

public class SocialNotification extends AppCompatActivity {

    // Notification Channel
    // Notification Builder
    // Notification Manager

    private static final String CHANNEL_ID = "Outlift_social";
    private static final String CHANNEL_NAME = "Outlift social channel name";
    private static final String CHANNEL_DESC = "Outlift Social Notification";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notification");
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        //Toast.makeText(SocialNotification.this, "exercises", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SocialNotification.this
                                , WorkoutListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.page_2:
                        //Toast.makeText(SocialNotification.this, "health", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.page_3:
                        //Toast.makeText(SocialNotification.this, "workout", Toast.LENGTH_SHORT).show();
                        //Context context = bottomNavigationView.getContext();
                        Intent intent3 = new Intent(SocialNotification.this
                                , MainActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.page_4:
                        //Toast.makeText(SocialNotification.this, "social", Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(SocialNotification.this, SocialNotification.class);
                        startActivity(intent4);
                        break;
                    case R.id.page_5:
                        //Toast.makeText(SocialNotification.this, "profile", Toast.LENGTH_SHORT).show();
                        Intent intent5 = new Intent(SocialNotification.this, ProfileActivity.class);
                        startActivity(intent5);
                        break;
                }
                return true;
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

//        findViewById(R.id.buttonNotify).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                displayNotification();
//            }
//        });



//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//
//                        if(task.isSuccessful()) {
//                            String token = task.getResult().getToken();
//
//                        } else {
//
//                        }
//                    }
//                });


    }

    private void displayNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_message)
                    .setContentTitle("Outlift Notification")
                    .setContentText("Workout with me!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, mBuilder.build());
    }
}
