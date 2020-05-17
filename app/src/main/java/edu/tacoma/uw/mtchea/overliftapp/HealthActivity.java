/**
 * Overlift App
 * Ross, Roman, Ilya, Mercedes
 * Group 2
 * TCSS 450
 */


package edu.tacoma.uw.mtchea.overliftapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Health/Diet activity class
 * @author Roman Ross
 * @version May 15, 2020
 */
public class HealthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.page_2);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        Toast.makeText(HealthActivity.this, "exercises", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HealthActivity.this
                                , ExerciseListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.page_2:
                        Toast.makeText(HealthActivity.this, "health", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(HealthActivity.this
                                , HealthActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.page_3:
                        Toast.makeText(HealthActivity.this, "workout", Toast.LENGTH_SHORT).show();
                        Context context = bottomNavigationView.getContext();
                        Intent intent3 = new Intent(HealthActivity.this
                                , MainActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.page_4:
                        Toast.makeText(HealthActivity.this, "social", Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(HealthActivity.this, SocialNotification.class);
                        startActivity(intent4);
                        break;
                    case R.id.page_5:
                        Toast.makeText(HealthActivity.this, "profile", Toast.LENGTH_SHORT).show();
                        Intent intent5 = new Intent(HealthActivity.this, ProfileActivity.class);
                        startActivity(intent5);
                        break;
                }
                return true;
            }
        });
    }
}
