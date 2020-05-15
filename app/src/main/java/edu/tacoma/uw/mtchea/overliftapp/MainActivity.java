package edu.tacoma.uw.mtchea.overliftapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Roman Kucheryavyy, Code Snippets from Professor Menaka Abraham webcourseservices lab
 *
 *
 * Main Activity class where the workouts and the main function of the app will be in, the activity where a user gets to
 * view her workout and time it, sort of like a homepage for the main functinality in the app which is to perform and organize a workout.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Workout");
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        /**
         * Creating bottom navigation bar with all its corresponding intents
         */
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        Toast.makeText(MainActivity.this, "exercises", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this
                                , ExerciseListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.page_2:
                        Toast.makeText(MainActivity.this, "health", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.page_3:
                        Toast.makeText(MainActivity.this, "workout", Toast.LENGTH_SHORT).show();
                        //Context context = bottomNavigationView.getContext();
                        Intent intent3 = new Intent(MainActivity.this
                                , MainActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.page_4:
                        Toast.makeText(MainActivity.this, "social", Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(MainActivity.this, SocialNotification.class);
                        startActivity(intent4);
                        break;
                    case R.id.page_5:
                        Toast.makeText(MainActivity.this, "social", Toast.LENGTH_SHORT).show();
                        Intent intent5 = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent5);
                        break;
                }
                return true;
            }
        });
    }
}


