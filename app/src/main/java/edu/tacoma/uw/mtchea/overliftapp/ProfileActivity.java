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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Profile Tab
 * @author Mercedes Chea
 * @version May 15, 2020
 */
public class ProfileActivity extends AppCompatActivity {

    /**
     * Firebase authorization required to signing/signup into Firebase.
     */
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.page_5);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
 //                       Toast.makeText(ProfileActivity.this, "exercises", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProfileActivity.this
                                , ExerciseListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.page_2:
  //                      Toast.makeText(ProfileActivity.this, "health", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(ProfileActivity.this
                                , HealthActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.page_3:
  //                      Toast.makeText(ProfileActivity.this, "workout", Toast.LENGTH_SHORT).show();
                        Context context = bottomNavigationView.getContext();
                        Intent intent3 = new Intent(ProfileActivity.this
                                , MainActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.page_4:
 //                       Toast.makeText(ProfileActivity.this, "social", Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(ProfileActivity.this, SocialActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.page_5:
  //                      Toast.makeText(ProfileActivity.this, "profile", Toast.LENGTH_SHORT).show();
                        Intent intent5 = new Intent(ProfileActivity.this, ProfileActivity.class);
                        startActivity(intent5);
                        break;
                }
                return true;
            }
        });


//        mAuth = FirebaseAuth.getInstance();
//
//        FirebaseUser user = mAuth.getCurrentUser();
//
//        String UID = user.getUid();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_tool_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.userLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent (this, SocialNotification.class));

                break;
        }
        return true;
    }


}
