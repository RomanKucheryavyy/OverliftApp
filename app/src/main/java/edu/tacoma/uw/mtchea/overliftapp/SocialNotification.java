package edu.tacoma.uw.mtchea.overliftapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;


/**
 * The main Social Notification Tab that contains the user log in.
 * To use the social tab and send messages/notifications to other people
 * initial sign up/log in is required.
 * @author Ilya Bokov
 * @version May 11, 2020
 */
public class SocialNotification extends AppCompatActivity {
    /**
     * Channel ID Used for creation of NotificationChannel.
     */
    public static final String CHANNEL_ID = "Outlift_social";

    /**
     * Channel Name Used for creation of NotificationChannel.
     */
    private static final String CHANNEL_NAME = "Outlift social channel name";

    /**
     * Channel Description Used for creation of NotificationChannel.
     */
    private static final String CHANNEL_DESC = "Outlift Social Notification";

    /**
     * EditText fields for Email and Password.
     */
    private EditText editTextEmail, editTextPassword;

    /**
     * Progress bar while the user is being signed up.
     */
    private ProgressBar progressBar;

    /**
     * Firebase authorization required to signing/signup into Firebase.
     */
    private FirebaseAuth mAuth;

    /**
     * Creates navigationbar,
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notification");

        // The navigation bar that lists all the tabs on the bottom.
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        //Toast.makeText(SocialNotification.this, "exercises", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SocialNotification.this
                                , ExerciseListActivity.class);
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

        // Gets the authorization from Firebase.
        mAuth = FirebaseAuth.getInstance();

        // Creates the NotificatonChannel used to display the notification.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        // initializing the progress bar and email/password texts.
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        // Once the button is clicked it invokes the createUser method.
        findViewById(R.id.buttonSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

    }

    /**
     * Create user method that receives users email and password and
     * passes it to Firebase to register the user.
     */
    private void createUser() {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()) {
            editTextEmail.setError("Email required");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6) {
            editTextPassword.setError("Password should be at least 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        // Firebase creating the user.
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            startSocialActivity();
                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                userLogin(email, password);
                            }else{
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(SocialNotification.this, task.getException()
                                        .getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    /**
     * Logs in the user and starts the SocialActivity.
     * @param email users email that was used to register.
     * @param password users password that was used to register.
     */
    private void userLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            startSocialActivity();
                        }else{
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SocialNotification.this, task.getException()
                                    .getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    /**
     * Checks if the user is already signed in.
     * If the user in not signed in send him to the login.
     */
    @Override
    protected void onStart() {
        super.onStart();

        // if user is not logged in send him to main login activity.
        if(mAuth.getCurrentUser() != null) {
            startSocialActivity();
        }

    }

    /**
     * Starts the social activity.
     */
    private void startSocialActivity() {
        Intent intent = new Intent(this, SocialActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
