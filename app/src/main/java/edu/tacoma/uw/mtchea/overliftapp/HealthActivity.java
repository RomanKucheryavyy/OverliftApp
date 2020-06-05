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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

import edu.tacoma.uw.mtchea.overliftapp.model.Meal;



/**
 * Health/Diet activity class
 * @author Roman Ross
 * @version May 15, 2020
 */
public class HealthActivity extends AppCompatActivity {
    private List<Meal> mMealList;

    public static final String ADD_MEAL = "Add_MEAL";
    private JSONObject mCourseJSON;
    public Button caloriesButton;
    public Button fatsButton;
    public Button carbsButton;
    public Button proteinButton;

    //ExerciseDetailActivity test = new ExerciseDetailActivity();
    //public Button fatsButton = (Button) findViewById(R.id.fats_button);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);


        caloriesButton = (Button) findViewById(R.id.calories_button);
        caloriesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                snackbarCalories();
            }
        });

        proteinButton = (Button) findViewById(R.id.protein_button);
        proteinButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                snackbarProtein();
            }
        });

        fatsButton = (Button) findViewById(R.id.fats_button);
        fatsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                snackbarFats();
            }
        });

        carbsButton = (Button) findViewById(R.id.carbs_button);
        carbsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                snackbarCarbs();
            }
        });
        final EditText editFood = findViewById(R.id.editFoodText);
        final EditText editCalories = findViewById(R.id.editCaloriesText);
        final EditText editCarbs = findViewById(R.id.editCarbsText);
        final EditText editProteins = findViewById(R.id.editProteinsText);
        final EditText editFats = findViewById(R.id.editFatsText);
        final EditText editQuantity = findViewById(R.id.editQuantityText);

        Button mealButton = (Button) findViewById(R.id.mealButton);
        mealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodName = editFood.getText().toString();
                int calories = Integer.parseInt(editCalories.getText().toString());
                int carbs = Integer.parseInt(editCarbs.getText().toString());
                int proteins = Integer.parseInt(editProteins.getText().toString());
                int fats = Integer.parseInt(editFats.getText().toString());
                int quantity = Integer.parseInt(editQuantity.getText().toString());


                Meal meal = new Meal(calories, carbs, fats, proteins, foodName, quantity, "ross1998@uw.edu");
                addMeal(meal);

                new MealTask().execute(getString(R.string.get_meals));
//                if (mAddListener != null) {
//                    mAddListener.addCourse(meal);
//                }
                //launchMealAddFragment();

            }
        });


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
                        Intent intent4 = new Intent(HealthActivity.this, SocialActivity.class);
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
    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (mMealList == null) {
                new MealTask().execute(getString(R.string.get_meals));
            }
        } else {
            Toast.makeText(this,
                    "No network connection available. Displaying locally stored data",
                    Toast.LENGTH_SHORT).show();

        }
    }

    public void addMeal(Meal meal) {
        StringBuilder url =  new StringBuilder(getString(R.string.add_meal));
        mCourseJSON = new JSONObject();
        try{

            mCourseJSON.put(Meal.CALORIES, meal.getCalories());
            mCourseJSON.put(Meal.CARBS, meal.getCarbs());
            mCourseJSON.put(Meal.FATS, meal.getFats());
            mCourseJSON.put(Meal.PROTEINS, meal.getProteins());
            mCourseJSON.put(Meal.NAME, meal.getFoodId());
            mCourseJSON.put(Meal.QUANTITY, meal.getQuantity());
            mCourseJSON.put(Meal.EMAIL, "ross1998@uw.edu");
            new AddMealAsyncTest().execute(url.toString());

        } catch (JSONException e){
            Toast.makeText(this, "Error with JSON creation on adding a course: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void snackbarCalories() {
        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.health),"Daily recommended 2500 calories", Snackbar.LENGTH_SHORT);
        mySnackbar.show();
    }
    private void snackbarProtein() {
        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.health),"Daily recommended Male: 56 grams, Female: 46 grams", Snackbar.LENGTH_SHORT);
        mySnackbar.show();
    }
    private void snackbarFats() {
        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.health),"Daily recommended 44-74 grams of fat", Snackbar.LENGTH_SHORT);
        mySnackbar.show();
    }
    private void snackbarCarbs() {
        Snackbar.make(findViewById(R.id.health), "Daily recommended 225 - 325 grams of carbohydrates",
                Snackbar.LENGTH_SHORT)
                .show();
    }

    private void launchMealAddFragment() {
    }

    private class MealTask extends AsyncTask<String, Void, String> {
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
                    //Log.i(ADD_MEAL, mCourseJSON.toString());
                    mCourseJSON = new JSONObject();
                    mCourseJSON.put("email", "ross1998@uw.edu");

                    wr.write(mCourseJSON.toString());
                    System.out.println(" TESTING! " + mCourseJSON.toString());
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
//
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
//                    response = "Unable to download the list of meals, Reason: "
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

        @Override
        protected void onPostExecute(String s) {
            if (s.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), "Unable to download" + s, Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                System.out.println("JASSSSOOOON " + jsonObject);
                if (jsonObject.getBoolean("success")) {
                    mMealList = Meal.parseCourseJson(
                            jsonObject.getString("names"));
                    System.out.println(mMealList);
                    int caloriesTemp = 0;
                    int fatsTemp = 0;
                    int proteinsTemp = 0;
                    int carbsTemp = 0;
                    for(int i = 0; i < mMealList.size(); i++){
                        caloriesTemp += mMealList.get(i).getCalories() * mMealList.get(i).getQuantity();
                        fatsTemp += mMealList.get(i).getFats() * mMealList.get(i).getQuantity();
                        proteinsTemp += mMealList.get(i).getProteins() * mMealList.get(i).getQuantity();
                        carbsTemp += mMealList.get(i).getCarbs() * mMealList.get(i).getQuantity();

                    }
                    caloriesButton.setText(caloriesTemp + "/2500");
                    fatsButton.setText("Fats\n" + fatsTemp + "(g)");
                    proteinButton.setText("Protein\n" + proteinsTemp + "(g)");
                    carbsButton.setText("Carbs\n" + carbsTemp + "(g)");


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

    private class AddMealAsyncTest extends AsyncTask<String, Void, String> {
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
                    Log.i(ADD_MEAL, mCourseJSON.toString());
                    wr.write(mCourseJSON.toString());
                    System.out.println(" TESTING! " + mCourseJSON.toString());
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
                    Toast.makeText(getApplicationContext(), "Meal Added successfully"
                            , Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Meal couldn't be added: "
                                    + jsonObject.getString("error")
                            , Toast.LENGTH_LONG).show();
                    Log.e(ADD_MEAL, jsonObject.getString("error"));
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Parsing error on Adding meal"
                                + e.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.e(ADD_MEAL, e.getMessage());
            }
        }
    }
}
