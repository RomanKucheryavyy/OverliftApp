package edu.tacoma.uw.mtchea.overliftapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

//import edu.tacoma.uw.courseswebservicesapp.data.CourseDB;
//import edu.tacoma.uw.courseswebservicesapp.model.Course;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import edu.tacoma.uw.mtchea.overliftapp.model.Exercise;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a { CourseDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class WorkoutListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private List<Exercise> mExercisesList;
    private RecyclerView mRecyclerView;
  //  private CourseDB mCourseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        mRecyclerView = findViewById(R.id.item_list);
        assert mRecyclerView != null;
        setupRecyclerView((RecyclerView) mRecyclerView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Excercises");
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        Toast.makeText(WorkoutListActivity.this, "exercises", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(WorkoutListActivity.this
                                , WorkoutListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.page_2:
                        Toast.makeText(WorkoutListActivity.this, "health", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.page_3:
                        Toast.makeText(WorkoutListActivity.this, "workout", Toast.LENGTH_SHORT).show();
                        //Context context = bottomNavigationView.getContext();
                        Intent intent3 = new Intent(WorkoutListActivity.this
                                , MainActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.page_4:
                        Toast.makeText(WorkoutListActivity.this, "social", Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(WorkoutListActivity.this, SocialNotification.class);
                        startActivity(intent4);
                        break;
                    case R.id.page_5:
                        Toast.makeText(WorkoutListActivity.this, "profile", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                launchCourseAddFragment();
//            }
//        });

//        if (findViewById(R.id.item_detail_container) != null) {
//            // The detail container view will be present only in the
//            // large-screen layouts (res/values-w900dp).
//            // If this view is present, then the
//            // activity should be in two-pane mode.
//            mTwoPane = true;
//        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (mExercisesList == null) {
                new CoursesTask().execute(getString(R.string.get_exercises));
            }
        }
//        else {
//            Toast.makeText(this,
//                    "No network connection available. Displaying locally stored data",
//                    Toast.LENGTH_SHORT).show();
//
//            if (mCourseDB == null) {
//                mCourseDB = new CourseDB(this);
//            }
//            if (mCourseList == null) {
//                mCourseList = mCourseDB.getCourses();
//                setupRecyclerView(mRecyclerView);
//
//            }
//        }
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        if(mExercisesList != null){
            recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, mExercisesList, mTwoPane));
        }

    }
    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final WorkoutListActivity mParentActivity;
        private final List<Exercise> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exercise item = (Exercise) view.getTag();
                if (mTwoPane) {
//                    Bundle arguments = new Bundle();
//                    arguments.putSerializable(CourseDetailFragment.ARG_ITEM_ID, item);
//                    CourseDetailFragment fragment = new CourseDetailFragment();
//                    fragment.setArguments(arguments);
//                    mParentActivity.getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.item_detail_container, fragment)
//                            .commit();
                } else {
//                    Context context = view.getContext();
//                    Intent intent = new Intent(context, CourseDetailActivity.class);
//                    intent.putExtra(CourseDetailFragment.ARG_ITEM_ID, item);
//
//                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(WorkoutListActivity parent,
                                      List<Exercise> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.course_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getExercise());
            holder.mContentView.setText(mValues.get(position).getTargetArea());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
    private class CoursesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to download the list of courses, Reason: "
                            + e.getMessage();
                }
                finally {
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

                if (jsonObject.getBoolean("success")) {
                    mExercisesList = Exercise.parseCourseJson(
                            jsonObject.getString("names"));
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

                    if (!mExercisesList.isEmpty()) {
                        setupRecyclerView((RecyclerView) mRecyclerView);
                    }
                }

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "JSON Error: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }

    }
//    private void launchCourseAddFragment(){
//        CourseAddFragment courseAddFragment = new CourseAddFragment();
//        if(mTwoPane){
//            getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, courseAddFragment).commit();
//        } else {
//            Intent intent = new Intent(this, CourseDetailActivity.class);
//            intent.putExtra(CourseDetailActivity.ADD_COURSE, true);
//            startActivity(intent);
//        }
//    }

}
