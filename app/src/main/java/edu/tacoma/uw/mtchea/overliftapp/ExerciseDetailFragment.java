package edu.tacoma.uw.mtchea.overliftapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import edu.tacoma.uw.mtchea.overliftapp.model.Exercise;



/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ExerciseListActivity}
 * in two-pane mode (on tablets) or a {@link ExerciseDetailActivity}
 * on handsets.
 */
public class ExerciseDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Exercise mExercise;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExerciseDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mExercise = (Exercise) getArguments().getSerializable(ARG_ITEM_ID);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mExercise.getExercise());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exercises_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mExercise != null) {
            ((TextView) rootView.findViewById(R.id.item_detail_short_target_area)).setText("Target Area: \n -> " + mExercise.getTargetArea());
            ((TextView) rootView.findViewById(R.id.item_detail_complexity)).setText("Complexity: \n -> " + mExercise.getComplexity());
            ((TextView) rootView.findViewById(R.id.item_detail_description)).setText("Description: \n -> \n" + mExercise.getDescription());
            ((TextView) rootView.findViewById(R.id.item_credit)).setText("Data Provided by: \n https://www.jefit.com/exercises/");
        }

        return rootView;
    }
}
