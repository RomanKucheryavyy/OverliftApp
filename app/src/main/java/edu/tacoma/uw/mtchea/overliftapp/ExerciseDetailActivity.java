/**
 * Overlift App
 * Ross, Roman, Ilya, Mercedes
 * Group 2
 * TCSS 450
 */
package edu.tacoma.uw.mtchea.overliftapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
 *
 * Exercises detail activity for creating the display where the details about our exercise will be held in
 * code snippets provided by Menaka Abraham, webcourses lab
 * @author Roman, Ross
 */
public class ExerciseDetailActivity extends AppCompatActivity  {
    public static final String ADD_COURSE = "Add_COURSE";
    private JSONObject mWorkoutJSON;
    private List<Workout> mWorkoutList;
    ExerciseDetailFragment fragment = new ExerciseDetailFragment();
    public static final String mypreference = "mypref";
    private SharedPreferences mSharedPreferences;
    public static final String emailKey = "emailKey";
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);

        mSharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        email = mSharedPreferences.getString(emailKey, "");
        System.out.println("EMAIL" + email);

//        setSupportActionBar(toolbar);
//        System.out.println("MY TITLE " + toolbar.getTitle());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.bookmark);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new WorkoutTask().execute(getString(R.string.add_workout));
               // HealthActivity test = new HealthActivity();
            }
        });

//        final CollapsingToolbarLayout title = findViewById(R.id.toolbar_layout);


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            if(getIntent().getSerializableExtra(ExerciseDetailFragment.ARG_ITEM_ID)!= null) {
                arguments.putSerializable(ExerciseDetailFragment.ARG_ITEM_ID,
                        getIntent().getSerializableExtra(ExerciseDetailFragment.ARG_ITEM_ID));
//                ExerciseDetailFragment fragment = new ExerciseDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container, fragment).commit();

            }
//            } else if (getIntent().getBooleanExtra(CourseDetailActivity.ADD_COURSE, false)) {
//                CourseAddFragment fragment = new CourseAddFragment();
//                getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container, fragment).commit();
//
//            }
        }
    }

    /**
     * When an option gets selected start intent
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ExerciseListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sync exercises
     */
    private class AddExerciseAsyncTask extends AsyncTask<String, Void, String> {
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

                    // For Debugging
                    Log.i(ADD_COURSE, mWorkoutJSON.toString());
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

        /**
         * Helper method for when we might add courses through the app
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            if (s.startsWith("Unable to add the new course")) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("success")) {
                    Toast.makeText(getApplicationContext(), "Course Added successfully"
                            , Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Course couldn't be added: "
                                    + jsonObject.getString("error")
                            , Toast.LENGTH_LONG).show();
                    Log.e(ADD_COURSE, jsonObject.getString("error"));
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Parsing error on Adding course"
                                + e.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.e(ADD_COURSE, e.getMessage());
            }
        }
    }

    private class WorkoutTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            System.out.println("TEXTTTT " + fragment.getExerciseName());
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

                    // For Debugging
                    //Log.i(ADD_MEAL, mCourseJSON.toString());

                    mWorkoutJSON = new JSONObject();
                    mWorkoutJSON.put("ename",fragment.getExerciseName());
                    mWorkoutJSON.put("email", email);

                    wr.write(mWorkoutJSON.toString());
                    System.out.println(" TESTING! " + mWorkoutJSON.toString());
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
            if (s.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), "Unable to download" + s, Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                //System.out.println("JASSSSOOOON " + jsonObject);
                if (jsonObject.getBoolean("success")) {
                    mWorkoutList = Workout.parseCourseJson(
                            jsonObject.getString("names"));
                    System.out.println(mWorkoutList);
//                    int caloriesTemp = 0;
//                    int fatsTemp = 0;
//                    int proteinsTemp = 0;
//                    int carbsTemp = 0;
//                    for(int i = 0; i < mWorkoutList.size(); i++){
//                        caloriesTemp += mWorkoutList.get(i).getCalories() * mWorkoutList.get(i).getQuantity();
//                        fatsTemp += mWorkoutList.get(i).getFats() * mWorkoutList.get(i).getQuantity();
//                        proteinsTemp += mWorkoutList.get(i).getProteins() * mWorkoutList.get(i).getQuantity();
//                        carbsTemp += mWorkoutList.get(i).getCarbs() * mWorkoutList.get(i).getQuantity();
//
//                    }
//                    caloriesButton.setText(caloriesTemp + "/2500");
//                    fatsButton.setText("Fats\n" + fatsTemp + "(g)");
//                    proteinButton.setText("Protein\n" + proteinsTemp + "(g)");
//                    carbsButton.setText("Carbs\n" + carbsTemp + "(g)");


//                    if (!mMealList.isEmpty()) {
//                        setupRecyclerView((RecyclerView) mRecyclerView);
//                    }
                }

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Error: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

}
