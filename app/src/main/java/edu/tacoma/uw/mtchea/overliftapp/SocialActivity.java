/**
 * Overlift App
 * Ross, Roman, Ilya, Mercedes
 * Group 2
 * TCSS 450
 */

package edu.tacoma.uw.mtchea.overliftapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity for showing all the users in the  social tab.
 * @author Ilya Bokov
 * @Version May 15, 2020
 */
public class SocialActivity extends AppCompatActivity {
    /**
     * Name of the node in Firebase realtime database.
     */
    public static final String NODE_USERS = "users";

    /**
     * Firebase authorization.
     */
    private FirebaseAuth mAuth;

    /**
     * A list of all the Users.
     */
    private List<UserSocial> userList;

    /**
     * A basic recyclerView used to help display all the users.
     */
    private RecyclerView recyclerView;

    /**
     * Progress bar that shows up while the users are being loaded from the database.
     */
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        progressBar = findViewById(R.id.progressbar_social);
        mAuth = FirebaseAuth.getInstance();

        FirebaseMessaging.getInstance().subscribeToTopic("updates");

        loadUsers();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            String token = task.getResult().getToken();
                            saveToken(token);
                        } else {

                        }
                    }
                });


//        The navigation bar that lists all the tabs on the bottom.
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setSelectedItemId(R.id.page_4);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        //Toast.makeText(SocialNotification.this, "exercises", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SocialActivity.this
                                , ExerciseListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.page_2:
                        //Toast.makeText(SocialNotification.this, "health", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(SocialActivity.this
                                , HealthActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.page_3:
                        //Toast.makeText(SocialNotification.this, "workout", Toast.LENGTH_SHORT).show();
//                        Context context = bottomNavigationView.getContext();
                        Intent intent3 = new Intent(SocialActivity.this
                                , MainActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.page_4:
                        //Toast.makeText(SocialNotification.this, "social", Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(SocialActivity.this, SocialActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.page_5:
                        //Toast.makeText(SocialNotification.this, "profile", Toast.LENGTH_SHORT).show();
                        Intent intent5 = new Intent(SocialActivity.this, ProfileActivity.class);
                        startActivity(intent5);
                        break;
                }
                return true;
            }
        });
    }

    /**
     * Helper method that uses recyclerView and access the FirebaseDatabase to load all the
     * users into the activity.
     */
    private void loadUsers() {
        progressBar.setVisibility(View.VISIBLE);
        userList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewSocial);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference("users");
        dbUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.INVISIBLE);
                if(dataSnapshot.exists()) {

                    for(DataSnapshot dsUser: dataSnapshot.getChildren()) {

                        UserSocial user = dsUser.getValue(UserSocial.class);
                        userList.add(user);
                    }

                    UserAdapter adapter = new UserAdapter(SocialActivity.this, userList);
                    recyclerView.setAdapter(adapter);
                } else{
                    Toast.makeText(SocialActivity.this, "No user found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    /**
     * onStart method that checks if the user is log in or not.
     * First time users will always see the login screen.
     */
    @Override
    protected void onStart() {
        super.onStart();

        // if user is not logged in send him to main login activity.
        if(mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, SocialNotification.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }

    /**
     * Saves the unique token that is generated when the user logs in.
     * @param token The unique token.
     */
    private void saveToken(String token) {
        String email = mAuth.getCurrentUser().getEmail();

        UserSocial user = new UserSocial(email, token);

        DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference(NODE_USERS);

        dbUsers.child(mAuth.getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SocialActivity.this, "Token Saved", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
