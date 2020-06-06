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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.tacoma.uw.mtchea.overliftapp.model.Meal;
import edu.tacoma.uw.mtchea.overliftapp.model.Profile;

/**
 * Profile Tab
 * @author Mercedes Chea
 * @version May 15, 2020
 */
public class ProfileActivity extends AppCompatActivity {

    /**
     * Profile String
     */
    public static final String ADD_PROFILE = "ADD_PROFILE";

    /**
     * Gender Key for Shared Preferences
     */
    public static final String nameKey = "nameKey";

    /**
     * Gender Key for Shared Preferences
     */
    public static final String emailKey = "emailKey";

    /**
     * Gender Key for Shared Preferences
     */
    public static final String genderKey = "genderKey";

    /**
     * Height Key for Shared Preferences
     */
    public static final String heightKey = "heightKey";

    /**
     * Weight Key for Shared Preferences
     */
    public static final String weightKey = "weightKey";

    /**
     * Age Key for Shared Preferences
     */
    public static final String ageKey = "ageKey";

    /**
     * String for Shared Preferences
     */
    public static final String mypreference = "mypref";

    /**
     * JSON Object for sending data
     */
    private JSONObject mCourseJSON;

    /**
     * Button for updating profile
     */
    public Button updateButton;

    /**
     * Firebase authorization required to signing/signup into Firebase.
     */
    private FirebaseAuth mAuth;

    /**
     * Shared preferences to save state
     */
    private SharedPreferences mSharedPreferences; // 0 - for private mode

    /**
     * onCreate method for Profile Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");

        updateButton = (Button) findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                snackbarUpdate();
            }
        });

        EditText editName = findViewById(R.id.editNameText);
        EditText editGender = findViewById(R.id.editGenderText);
        EditText editHeight = findViewById(R.id.editHeightText);
        EditText editWeight = findViewById(R.id.editWeightText);
        EditText editAge = findViewById(R.id.editAgeText);
        TextView getEmail = findViewById(R.id.emailText);

        TextView userName = (TextView) findViewById(R.id.editNameText);
        TextView loginEmail = (TextView) findViewById(R.id.emailText);
        TextView userGender = (TextView) findViewById(R.id.editGenderText);
        TextView userHeight = (TextView) findViewById(R.id.editHeightText);
        TextView userWeight = (TextView) findViewById(R.id.editWeightText);
        TextView userAge = (TextView) findViewById(R.id.editAgeText);

        mSharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        if (mSharedPreferences.contains(nameKey)) {
            userName.setText(mSharedPreferences.getString(nameKey, ""));
        }

        if (mSharedPreferences.contains(emailKey)) {
            loginEmail.setText(mSharedPreferences.getString(emailKey, ""));
        }

        if (mSharedPreferences.contains(genderKey)) {
            userGender.setText(mSharedPreferences.getString(genderKey, ""));
        }

        if (mSharedPreferences.contains(ageKey)) {
            userAge.setText(Integer.toString(mSharedPreferences.getInt(ageKey, 0)));
        }

        if (mSharedPreferences.contains(heightKey)) {
            userHeight.setText(mSharedPreferences.getString(heightKey, ""));
        }

        if (mSharedPreferences.contains(weightKey)) {
            userWeight.setText(mSharedPreferences.getString(weightKey, ""));
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // adds profile data to database
                String name = editName.getText().toString();
                String gender = editGender.getText().toString();
                System.out.println(gender);
                String height = editHeight.getText().toString();
                System.out.println(height);
                String weight = editWeight.getText().toString();
                System.out.println(weight);
                System.out.println("SHOULD PRINT AFTER THIS");
                System.out.println(editAge.getText());
                int age = Integer.parseInt(editAge.getText().toString());
                System.out.println(age);

                String email = mSharedPreferences.getString(emailKey, "");
                System.out.println(email);

                Profile profile = new Profile(name, email, height, weight, gender, age, "password");
                System.out.println("BEFORE ADD PROFILE");
                addProfile(profile);
                System.out.println("AFTER ADD PROFILE");


                // saves profile data using shared preferences
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString(nameKey, name);
                editor.putString(genderKey, gender);
                editor.putString(heightKey, height);
                editor.putString(weightKey, weight);
                editor.putInt(ageKey, age);
                editor.commit();

                mSharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

                if (mSharedPreferences.contains(genderKey)) {
                    userGender.setText(mSharedPreferences.getString(genderKey, ""));
                }

            }
        });

        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.page_5);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        Intent intent = new Intent(ProfileActivity.this
                                , ExerciseListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.page_2:
                        Intent intent2 = new Intent(ProfileActivity.this
                                , HealthActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.page_3:
                        Context context = bottomNavigationView.getContext();
                        Intent intent3 = new Intent(ProfileActivity.this
                                , MainActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.page_4:
                        Intent intent4 = new Intent(ProfileActivity.this, SocialActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.page_5:
                        Intent intent5 = new Intent(ProfileActivity.this, ProfileActivity.class);
                        startActivity(intent5);
                        break;
                }
                return true;
            }
        });

        Intent incomingLoginIntent = getIntent();
        String userEmail = incomingLoginIntent.getStringExtra("userEmail");
        String userPassword = incomingLoginIntent.getStringExtra("userPassword");


//        mAuth = FirebaseAuth.getInstance();
//
//        FirebaseUser user = mAuth.getCurrentUser();
//
//        String UID = user.getUid();
    }

    /**
     * Creates a snackbar for update button
     */
    private void snackbarUpdate() {
        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.health),"Profile has been updated", Snackbar.LENGTH_SHORT);
        mySnackbar.show();
    }

    /**
     * onCreateOptionsMenu for Profile activity
     * @param menu Menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_tool_bar_menu, menu);
        return true;
    }

    /**
     * onOptionsItemSelected for Profile Activity
     * @param item MenuItem
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.userLogout:
                FirebaseAuth.getInstance().signOut();

                mSharedPreferences.edit().remove(nameKey).commit();
                mSharedPreferences.edit().remove(genderKey).commit();
                mSharedPreferences.edit().remove(heightKey).commit();
                mSharedPreferences.edit().remove(weightKey).commit();
                mSharedPreferences.edit().remove(ageKey).commit();
                finish();
                startActivity(new Intent (this, SocialNotification.class));

                break;

            case R.id.userShare:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                StringBuilder shareBody = new StringBuilder();
                sharingIntent.setType("text/plain");
                shareBody.append("Name: ");
                shareBody.append(mSharedPreferences.getString(nameKey, "name"));
                shareBody.append("\n");
                shareBody.append("Gender: ");
                shareBody.append(mSharedPreferences.getString(genderKey, "gender"));
                shareBody.append("\n");
                shareBody.append("Height: ");
                shareBody.append(mSharedPreferences.getString(heightKey, "height"));
                shareBody.append("\n");
                shareBody.append("Weight: ");
                shareBody.append(mSharedPreferences.getString(weightKey, "weight"));
                shareBody.append("\n");
                shareBody.append("Age: ");
                shareBody.append(Integer.toString(mSharedPreferences.getInt(ageKey, 0)));
                String body = (String) shareBody.toString();
                String shareSubject = "This is my profile information";
                sharingIntent.putExtra(Intent.EXTRA_TEXT, body);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);

                startActivity(Intent.createChooser(sharingIntent, "Share Using"));
                break;
        }
        return true;
    }

    /**
     * adds profile to database
     * @param profile user's profile information
     */
    public void addProfile(Profile profile) {
        StringBuilder url = new StringBuilder("https://ross1998-project-backend.herokuapp.com/register");

        //Construct a JSONObject to build a formatted message to send.
        mCourseJSON = new JSONObject();
        try {
            mCourseJSON.put(Profile.NAME, profile.getName());
            mCourseJSON.put(Profile.EMAIL, profile.getEmail());
            mCourseJSON.put(Profile.HEIGHT, profile.getHeight());
            mCourseJSON.put(Profile.WEIGHT, profile.getWeight());
            mCourseJSON.put(Profile.GENDER, profile.getGender());
            mCourseJSON.put(Profile.AGE, profile.getAge());
            mCourseJSON.put(Profile.PASSWORD, "password");
            new ProfileTask().execute(url.toString());
        } catch (JSONException e) {
            Toast.makeText(this, "Error with JSON creation on adding a course: " + e.getMessage()
                    , Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Async Profile Task
     */
    private class ProfileTask extends AsyncTask<String, Void, String> {
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

                    Log.i(ADD_PROFILE, mCourseJSON.toString());
                    wr.write(mCourseJSON.toString());
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
         * onPostExecute method
         * @param s String
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
                    Toast.makeText(getApplicationContext(), "Profile Added successfully"
                            , Toast.LENGTH_SHORT).show();
//                    EditText editText = (EditText)findViewById(R.id.editGenderText);
//                    editText.setText(getGender(), TextView.BufferType.EDITABLE);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Profile couldn't be added: "
                                    + jsonObject.getString("error")
                            , Toast.LENGTH_LONG).show();
                    Log.e(ADD_PROFILE, jsonObject.getString("error"));
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Parsing error on Adding profile"
                                + e.getMessage()
                        , Toast.LENGTH_LONG).show();
                Log.e(ADD_PROFILE, e.getMessage());
            }
        }
    }

}
