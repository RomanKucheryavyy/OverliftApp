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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

import edu.tacoma.uw.mtchea.overliftapp.model.Profile;

/**
 * Profile Tab
 * @author Mercedes Chea
 * @version May 15, 2020
 */
public class ProfileActivity extends AppCompatActivity {

    /**
     *
     */
    public static final String ADD_PROFILE = "ADD_PROFILE";
    private JSONObject mCourseJSON;

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

        Intent incomingLoginIntent = getIntent();
        String userEmail = incomingLoginIntent.getStringExtra("userEmail");
        String userPassword = incomingLoginIntent.getStringExtra("userPassword");


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

    public void addProfile(Profile profile) {
        StringBuilder url = new StringBuilder("https://ross1998-project-backend.herokuapp.com/register");

        //Construct a JSONObject to build a formatted message to send.
        mCourseJSON = new JSONObject();
        try {
            mCourseJSON.put(Profile.NAME, profile.getName());
            mCourseJSON.put(Profile.EMAIL, profile.getEmail());
            mCourseJSON.put(Profile.HEIGHT, profile.getHeight());
            mCourseJSON.put(Profile.WEIGHT, profile.getWeight());
            mCourseJSON.put(Profile.AGE, profile.getAge());
            mCourseJSON.put(Profile.PASSWORD, profile.getPassword());
            new AddCourseAsyncTask().execute(url.toString());
        } catch (JSONException e) {
            Toast.makeText(this, "Error with JSON creation on adding a course: " + e.getMessage()
                    , Toast.LENGTH_SHORT).show();
        }
    }

    private class AddCourseAsyncTask extends AsyncTask<String, Void, String> {
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
