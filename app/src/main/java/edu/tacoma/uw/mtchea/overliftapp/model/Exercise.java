package edu.tacoma.uw.mtchea.overliftapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Exercise implements Serializable {
    private String mExercise;
    private String mTargetArea;
    private String mComplexity;
    private String mDescription;

    public static final String NAME = "ename";
    public static final String TARGET_AREA = "targetarea";
    public static final String COMPLEXITY = "complexity";
    public static final String DESCRIPTION = "edescription";

    public Exercise(String s1, String s2, String s3, String s4){

        mExercise = s1;
        mTargetArea = s2;
        mComplexity = s3;
        mDescription = s4;

    }

    public String getExercise() {
        return mExercise;
    }

    public void setCourseId(String mCourseId) {
        this.mExercise = mCourseId;
    }

    public String getTargetArea() {
        return mTargetArea;
    }

    public void setCourseShortDesc(String mCourseShortDesc) {
        this.mTargetArea = mCourseShortDesc;
    }

    public String getComplexity() {
        return mComplexity;
    }

    public void setCourseLongDesc(String mCourseLongDesc) {
        this.mComplexity = mCourseLongDesc;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setCoursePrereqs(String mCoursePrereqs) {
        this.mDescription = mCoursePrereqs;
    }

    public static List<Exercise> parseCourseJson(String courseJson) throws JSONException {
        List<Exercise> exerciseList = new ArrayList<>();
        if(courseJson != null){

            JSONArray arr = new JSONArray(courseJson);

            for(int i = 0; i < arr.length(); i++){
                JSONObject obj = arr.getJSONObject(i);
                Exercise exercise = new Exercise(obj.getString(Exercise.NAME), obj.getString(Exercise.TARGET_AREA),
                        obj.getString(Exercise.COMPLEXITY), obj.getString(Exercise.DESCRIPTION));
                exerciseList.add(exercise);

            }



        }
        return exerciseList;
    }
}
