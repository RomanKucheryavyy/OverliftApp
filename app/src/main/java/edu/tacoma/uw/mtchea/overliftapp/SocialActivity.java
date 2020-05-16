package edu.tacoma.uw.mtchea.overliftapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
