package edu.tacoma.uw.mtchea.overliftapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

//import edu.tacoma.uw.courseswebservicesapp.data.CourseDB;
//import edu.tacoma.uw.courseswebservicesapp.model.Course;

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
//    private List<Course> mCourseList;
    private RecyclerView mRecyclerView;
  //  private CourseDB mCourseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Workouts");

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

        mRecyclerView = findViewById(R.id.item_list);
        assert mRecyclerView != null;
        setupRecyclerView((RecyclerView) mRecyclerView);
    }
    @Override
    protected void onResume() {
        super.onResume();
//        ConnectivityManager connMgr = (ConnectivityManager)
//                getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            if (mCourseList == null) {
//                new CoursesTask().execute(getString(R.string.get_courses));
//            }
//        }
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
//        if(mCourseList != null){
//            recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, mCourseList, mTwoPane));
//        }

    }
//    public static class SimpleItemRecyclerViewAdapter
//            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
//
//        private final CourseListActivity mParentActivity;
//        private final List<Course> mValues;
//        private final boolean mTwoPane;
//        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Course item = (Course) view.getTag();
//                if (mTwoPane) {
//                    Bundle arguments = new Bundle();
//                    arguments.putSerializable(CourseDetailFragment.ARG_ITEM_ID, item);
//                    CourseDetailFragment fragment = new CourseDetailFragment();
//                    fragment.setArguments(arguments);
//                    mParentActivity.getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.item_detail_container, fragment)
//                            .commit();
//                } else {
//                    Context context = view.getContext();
//                    Intent intent = new Intent(context, CourseDetailActivity.class);
//                    intent.putExtra(CourseDetailFragment.ARG_ITEM_ID, item);
//
//                    context.startActivity(intent);
//                }
//            }
//        };
//
//        SimpleItemRecyclerViewAdapter(CourseListActivity parent,
//                                      List<Course> items,
//                                      boolean twoPane) {
//            mValues = items;
//            mParentActivity = parent;
//            mTwoPane = twoPane;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.course_list_content, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, int position) {
//            holder.mIdView.setText(mValues.get(position).getCourseId());
//            holder.mContentView.setText(mValues.get(position).getCourseShortDesc());
//
//            holder.itemView.setTag(mValues.get(position));
//            holder.itemView.setOnClickListener(mOnClickListener);
//        }
//
//        @Override
//        public int getItemCount() {
//            return mValues.size();
//        }
//
//        class ViewHolder extends RecyclerView.ViewHolder {
//            final TextView mIdView;
//            final TextView mContentView;
//
//            ViewHolder(View view) {
//                super(view);
//                mIdView = (TextView) view.findViewById(R.id.id_text);
//                mContentView = (TextView) view.findViewById(R.id.content);
//            }
//        }
//    }
//    private class CoursesTask extends AsyncTask<String, Void, String>{
//
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
//
//        @Override
//        protected void onPostExecute(String s) {
//            if (s.startsWith("Unable to")) {
//                Toast.makeText(getApplicationContext(), "Unable to download" + s, Toast.LENGTH_SHORT)
//                        .show();
//                return;
//            }
//            try {
//                JSONObject jsonObject = new JSONObject(s);
//
//                if (jsonObject.getBoolean("success")) {
//                    mCourseList = Course.parseCourseJson(
//                            jsonObject.getString("names"));
//                    if (mCourseDB == null) {
//                        mCourseDB = new CourseDB(getApplicationContext());
//                    }
//
//                    // Delete old data so that you can refresh the local
//                    // database with the network data.
//                    mCourseDB.deleteCourses();
//
//                    // Also, add to the local database
//                    for (int i=0; i<mCourseList.size(); i++) {
//                        Course course = mCourseList.get(i);
//                        mCourseDB.insertCourse(course.getCourseId(),
//                                course.getCourseShortDesc(),
//                                course.getCourseLongDesc(),
//                                course.getCoursePrereqs());
//                    }
//
//                    if (!mCourseList.isEmpty()) {
//                        setupRecyclerView((RecyclerView) mRecyclerView);
//                    }
//                }
//
//            } catch (JSONException e) {
//                Toast.makeText(getApplicationContext(), "JSON Error: " + e.getMessage(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    }
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
