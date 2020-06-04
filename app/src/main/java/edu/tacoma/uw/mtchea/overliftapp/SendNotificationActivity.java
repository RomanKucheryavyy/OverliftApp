/**
 * Overlift App
 * Ross, Roman, Ilya, Mercedes
 * Group 2
 * TCSS 450
 */

package edu.tacoma.uw.mtchea.overliftapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The main class that sends the notification to another phone.
 * @author Ilya Bokov
 * @version May 15, 2020
 */
public class SendNotificationActivity extends AppCompatActivity {
    /**
     * The user email.
     */
    private TextView textViewUser;

    /**
     * The notification/message title and body fields.
     */
    private EditText editTextTitle, editTextBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        final UserSocial user = (UserSocial) getIntent().getSerializableExtra("user");


        textViewUser = findViewById(R.id.textViewUser);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextBody = findViewById(R.id.editTextBody);

        textViewUser.setText("Sending to : " + user.email);

        findViewById(R.id.SendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification(user);
            }
        });

//         The navigation bar that lists all the tabs on the bottom.
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.page_4);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        //Toast.makeText(SocialNotification.this, "exercises", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SendNotificationActivity.this
                                , ExerciseListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.page_2:
                        //Toast.makeText(SocialNotification.this, "health", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(SendNotificationActivity.this
                                , HealthActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.page_3:
                        //Toast.makeText(SocialNotification.this, "workout", Toast.LENGTH_SHORT).show();
                        //Context context = bottomNavigationView.getContext();
                        Intent intent3 = new Intent(SendNotificationActivity.this
                                , MainActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.page_4:
                        //Toast.makeText(SocialNotification.this, "social", Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(SendNotificationActivity.this, SocialNotification.class);
                        startActivity(intent4);
                        break;
                    case R.id.page_5:
                        //Toast.makeText(SocialNotification.this, "profile", Toast.LENGTH_SHORT).show();
                        Intent intent5 = new Intent(SendNotificationActivity.this, ProfileActivity.class);
                        startActivity(intent5);
                        break;
                }
                return true;
            }
        });

    }

    /**
     * Receives a user email and checks if the fields like title and body are not empty.
     * Then uses REST API that I created to connect to Firebase and send the notification over.
     * @param user the user's email being passed in.
     */
    private void sendNotification(UserSocial user) {
        String title = editTextTitle.getText().toString().trim();
        String body = editTextBody.getText().toString().trim();

        if(title.isEmpty()) {
            editTextTitle.setError("Title required");
            editTextTitle.requestFocus();
            return;
        }

        if(body.isEmpty()) {
            editTextBody.setError("A message is required");
            editTextBody.requestFocus();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-overliftapp.cloudfunctions.net/app/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // The rest api
        Api api = retrofit.create(Api.class);

        Call<ResponseBody> call = api.sendNotification(user.token, title, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Toast.makeText(SendNotificationActivity.this, response.body().string(), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }


}
