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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Main Activity for displaying workouts code snippets provided by Menaka Abraham, webcourses lab
 * @author Ilya, Roman, Ross, Mercedes
 * @version May 15, 2020
 */
public class MainActivity extends AppCompatActivity {

    Button buttonStart, buttonStop;
    TextView timer;
    Handler customHandler = new Handler();
    RelativeLayout container;

    long startTime = 0L, timeInSeconds = 0L, updateTime = 0L, timeSwapBuff = 0L, timeInMilliseconds = 0L;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updateTime/1000);
            int mins = secs/60;
            secs%=60;
            int milliseconds = (int) (updateTime%1000);
            timer.setText(""+mins+":"+String.format("%2d",secs)+":"+String.format("%3d",milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };

    /**
     * on create method for main activity, currently only going to display "Today's Workout"
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);
        timer = (TextView) findViewById(R.id.timer);
        container = (RelativeLayout) findViewById(R.id.container);


        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
            }
        });
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Workout");
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.page_3);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
//                        bottomNavigationView.setSelected(false);
//                        bottomNavigationView.setSelectedItemId(R.id.page_1);
                        Toast.makeText(MainActivity.this, "exercises", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this
                                , ExerciseListActivity.class);
                        startActivity(intent);

                        break;
                    case R.id.page_2:
//                        bottomNavigationView.setSelected(false);
//                        bottomNavigationView.setSelectedItemId(R.id.page_2);
                        Toast.makeText(MainActivity.this, "health", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(MainActivity.this
                                , HealthActivity.class);
                        startActivity(intent2);

                        break;
                    case R.id.page_3:
//                        bottomNavigationView.setSelected(false);
//                        bottomNavigationView.setSelectedItemId(R.id.page_3);
                        Toast.makeText(MainActivity.this, "workout", Toast.LENGTH_SHORT).show();
                        //Context context = bottomNavigationView.getContext();
                        Intent intent3 = new Intent(MainActivity.this
                                , MainActivity.class);
                        startActivity(intent3);

                        break;
                    case R.id.page_4:
//                        bottomNavigationView.setSelected(false);
//                        bottomNavigationView.setSelectedItemId(R.id.page_4);
                        Toast.makeText(MainActivity.this, "social", Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(MainActivity.this, SocialNotification.class);
                        startActivity(intent4);

                        break;
                    case R.id.page_5:
//                        bottomNavigationView.setSelected(false);
//                        bottomNavigationView.setSelectedItemId(R.id.page_5);
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


