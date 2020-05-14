package edu.tacoma.uw.mtchea.overliftapp;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Workout");
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        Toast.makeText(MainActivity.this, "exercises", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this
                                , WorkoutListActivity.class);
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
                        Toast.makeText(MainActivity.this, "profile", Toast.LENGTH_SHORT).show();

                        break;
                }
                return true;
            }
        });
    }
}


