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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import edu.tacoma.uw.mtchea.overliftapp.model.Workout;

/**
 * Main Activity for displaying workouts code snippets provided by Menaka Abraham, webcourses lab
 * @author Ilya, Roman, Ross, Mercedes
 * @version May 15, 2020
 */
public class MainActivity extends AppCompatActivity {

    private String deleteString;
    private JSONObject mWorkoutJSON;
    private List<Workout> mExercisesList;
    Button buttonStart, buttonStop;
    TextView timer;
    Handler customHandler = new Handler();
    RelativeLayout container;
    public CheckedTextView check1;
    public CheckedTextView check2;
    public CheckedTextView check3;
    public CheckedTextView check4;
    public CheckedTextView check5;
    public CheckedTextView check6;
    public CheckedTextView check7;
    Button d1,d2,d3,d4,d5,d6,d7;

    long startTime = 0L, timeInSeconds = 0L, updateTime = 0L, timeSwapBuff = 0L, timeInMilliseconds = 0L;
    /**
     * Runnable for creating a stop watch
     */
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


        check1 =(CheckedTextView) findViewById(R.id.checkedTextView1);
        check2 = (CheckedTextView)findViewById(R.id.checkedTextView2);
        check3 = (CheckedTextView) findViewById(R.id.checkedTextView3);
        check4 = (CheckedTextView)findViewById(R.id.checkedTextView4);
        check5 = (CheckedTextView)findViewById(R.id.checkedTextView5);
        check6 = (CheckedTextView)findViewById(R.id.checkedTextView6);
        check7 = (CheckedTextView)findViewById(R.id.checkedTextView7);

        d1 = (Button)findViewById(R.id.d1);
        d2 = (Button)findViewById(R.id.d2);
        d3 = (Button)findViewById(R.id.d3);
        d4 = (Button)findViewById(R.id.d4);
        d5 = (Button)findViewById(R.id.d5);
        d6 = (Button)findViewById(R.id.d6);
        d7 = (Button)findViewById(R.id.d7);


//        CheckedTextView chkBox = (CheckedTextView) findViewById(R.id.checkedTextView1);
//        chkBox.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v)
//            {
//                System.out.println("SOMETHING");
//                ((CheckedTextView) v).toggle();
//                if(check2.isChecked() != true){
//                    check2.setCheckMarkDrawable(R.drawable.check);
//                    check2.setChecked(true);
//                } else {
//                    check2.setChecked(false);
//                }
//            }
//        });
        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteString = check1.getText().toString();
                System.out.println("DELETE STRING : " + deleteString);
                StringBuilder url =  new StringBuilder(getString(R.string.delete_workout));
                new DeleteTask().execute(url.toString());
            }
        });
        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteString = check2.getText().toString();
                StringBuilder url =  new StringBuilder(getString(R.string.delete_workout));
                new DeleteTask().execute(url.toString());
            }
        });
        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteString = check3.getText().toString();
                StringBuilder url =  new StringBuilder(getString(R.string.delete_workout));
                new DeleteTask().execute(url.toString());
            }
        });
        d4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteString = check4.getText().toString();
                StringBuilder url =  new StringBuilder(getString(R.string.delete_workout));
                new DeleteTask().execute(url.toString());
            }
        });
        d5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteString = check5.getText().toString();
                StringBuilder url =  new StringBuilder(getString(R.string.delete_workout));
                new DeleteTask().execute(url.toString());
            }
        });
        d6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteString = check6.getText().toString();
                StringBuilder url =  new StringBuilder(getString(R.string.delete_workout));
                new DeleteTask().execute(url.toString());
            }
        });
        d7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteString = check7.getText().toString();
                StringBuilder url =  new StringBuilder(getString(R.string.delete_workout));
                new DeleteTask().execute(url.toString());
            }
        });



        check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckedTextView) v).toggle();
                if(check1.isChecked()){
                    check1.setCheckMarkDrawable(R.drawable.delete);
                    check1.setChecked(false);
                    ((CheckedTextView) v).toggle();
                } else {
                    check1.setCheckMarkDrawable(R.drawable.check);
                    check1.setChecked(true);
                    ((CheckedTextView) v).toggle();

                }

            }
        });
        check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckedTextView) v).toggle();
                if(check2.isChecked()){
                    check2.setCheckMarkDrawable(R.drawable.delete);
                    check2.setChecked(false);
                    ((CheckedTextView) v).toggle();
                } else {
                    check2.setCheckMarkDrawable(R.drawable.check);
                    check2.setChecked(true);
                    ((CheckedTextView) v).toggle();

                }

            }
        });
        check3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckedTextView) v).toggle();
                if(check3.isChecked()){
                    check3.setCheckMarkDrawable(R.drawable.delete);
                    check3.setChecked(false);
                    ((CheckedTextView) v).toggle();
                } else {
                    check3.setCheckMarkDrawable(R.drawable.check);
                    check3.setChecked(true);
                    ((CheckedTextView) v).toggle();

                }

            }
        });
        check4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckedTextView) v).toggle();
                if(check4.isChecked()){
                    check4.setCheckMarkDrawable(R.drawable.delete);
                    check4.setChecked(false);
                    ((CheckedTextView) v).toggle();
                } else {
                    check4.setCheckMarkDrawable(R.drawable.check);
                    check4.setChecked(true);
                    ((CheckedTextView) v).toggle();

                }

            }
        });
        check5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckedTextView) v).toggle();
                if(check5.isChecked()){
                    check5.setCheckMarkDrawable(R.drawable.delete);
                    check5.setChecked(false);
                    ((CheckedTextView) v).toggle();
                } else {
                    check5.setCheckMarkDrawable(R.drawable.check);
                    check5.setChecked(true);
                    ((CheckedTextView) v).toggle();

                }

            }
        });
        check6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckedTextView) v).toggle();
                if(check6.isChecked()){
                    check6.setCheckMarkDrawable(R.drawable.delete);
                    check6.setChecked(false);
                    ((CheckedTextView) v).toggle();
                } else {
                    check6.setCheckMarkDrawable(R.drawable.check);
                    check6.setChecked(true);
                    ((CheckedTextView) v).toggle();

                }

            }
        });
        check7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CheckedTextView) v).toggle();
                if(check7.isChecked()){
                    check7.setCheckMarkDrawable(R.drawable.delete);
                    check7.setChecked(false);
                    ((CheckedTextView) v).toggle();
                } else {
                    check7.setCheckMarkDrawable(R.drawable.check);
                    check7.setChecked(true);
                    ((CheckedTextView) v).toggle();

                }

            }
        });

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
                        Intent intent4 = new Intent(MainActivity.this, SocialActivity.class);
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
        new WorkoutTask().execute(getString(R.string.get_workout));
    }
    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (mExercisesList == null) {
                new WorkoutTask().execute(getString(R.string.get_workout));
            }
        }
    }
//    public void addWorkout(Workout workout) {
//        StringBuilder url =  new StringBuilder(getString(R.string.get_workout));
//        mWorkoutJSON = new JSONObject();
//        try{
//
//            mWorkoutJSON.put(Workout.NAME, workout.getExerciseName());
//            mWorkoutJSON.put(Workout.EMAIL, "ross1998@uw.edu");
//            new WorkoutTask().execute(url.toString());
//
//        } catch (JSONException e){
//            Toast.makeText(this, "Error with JSON creation on adding a course: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }

    private class WorkoutTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setDoOutput(true);
                    OutputStreamWriter wr =
                            new OutputStreamWriter(urlConnection.getOutputStream());

                    mWorkoutJSON = new JSONObject();
                    mWorkoutJSON.put("email", "ross1998@uw.edu");
                    // For Debugging
                    wr.write(mWorkoutJSON.toString());
                    wr.flush();
                    wr.close();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to add the new course, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }
//        @Override
//        protected String doInBackground(String... urls) {
//            String response = "";
//            HttpURLConnection urlConnection = null;
//            for (String url : urls) {
//                try {
//                    URL urlObject = new URL(url);
//                    urlConnection = (HttpURLConnection) urlObject.openConnection();
//
//                    InputStream content = urlConnection.getInputStream();
//
//                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
//                    String s = "";
//                    while ((s = buffer.readLine()) != null) {
//                        response += s;
//                    }
//
//                } catch (Exception e) {
//                    response = "Unable to download the list of courses, Reason: "
//                            + e.getMessage();
//                }
//                finally {
//                    if (urlConnection != null)
//                        urlConnection.disconnect();
//                }
//            }
//            return response;
//
//        }

        @SuppressLint("WrongViewCast")
        @Override
        protected void onPostExecute(String s) {
            if (s.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), "Unable to download" + s, Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getBoolean("success")) {
                    mExercisesList = Workout.parseCourseJson(
                            jsonObject.getString("names"));
                    System.out.println("exercise LIST " + mExercisesList);

                    for(int i = 0; i < mExercisesList.size();i++){
                        if(i == 0){
                            check1.setText(mExercisesList.get(i).getExerciseName());
                        } else if (i == 1) {
                            check2.setText(mExercisesList.get(i).getExerciseName());
                        } else if (i == 2) {
                            check3.setText(mExercisesList.get(i).getExerciseName());
                        } else if (i == 3) {
                            check4.setText(mExercisesList.get(i).getExerciseName());
                        } else if (i == 4) {
                            check5.setText(mExercisesList.get(i).getExerciseName());
                        } else if (i == 5) {
                            check6.setText(mExercisesList.get(i).getExerciseName());
                        } else if (i == 6) {
                            check7.setText(mExercisesList.get(i).getExerciseName());
                        }
                    }
                    //check1.setText(mExercisesList.get(1).getExercise());


//                    if (mCourseDB == null) {
//                        mCourseDB = new CourseDB(getApplicationContext());
//                    }

                    // Delete old data so that you can refresh the local
                    // database with the network data.
                    //                  mCourseDB.deleteCourses();

                    // Also, add to the local database
//                    for (int i=0; i<mExercisesList.size(); i++) {
//                        Exercise course = mExercisesList.get(i);
//                        mCourseDB.insertCourse(course.getCourseId(),
//                                course.getCourseShortDesc(),
//                                course.getCourseLongDesc(),
//                                course.getCoursePrereqs());
//                    }

                }

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Error: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    private class DeleteTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setDoOutput(true);
                    OutputStreamWriter wr =
                            new OutputStreamWriter(urlConnection.getOutputStream());

                    mWorkoutJSON = new JSONObject();
                    mWorkoutJSON.put("email", "ross1998@uw.edu");
                    mWorkoutJSON.put("ename", deleteString);
                    System.out.println(mWorkoutJSON);
                    // For Debugging
                    wr.write(mWorkoutJSON.toString());
                    wr.flush();
                    wr.close();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to add the new course, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            if (s.startsWith("Unable to add the new course")) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("success")) {
                    Toast.makeText(getApplicationContext(), "Workout Deleted successfully"
                            , Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Workout couldn't be deleted: "
                                    + jsonObject.getString("error")
                            , Toast.LENGTH_LONG).show();
                    //Log.e(ADD_MEAL, jsonObject.getString("error"));
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Parsing error on deleting workout"
                                + e.getMessage()
                        , Toast.LENGTH_LONG).show();
                //Log.e(ADD_MEAL, e.getMessage());
            }
        }

    }
}


