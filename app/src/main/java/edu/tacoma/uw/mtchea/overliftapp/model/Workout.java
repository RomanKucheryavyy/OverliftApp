package edu.tacoma.uw.mtchea.overliftapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Kucheryavyy
 * Workout
 */
public class Workout implements Serializable {
    private String ename;
    private String email;

    public static final String NAME = "ename";
    public static final String EMAIL = "email";

    public Workout(String s1, String s2){

        ename = s1;
        email = s2;

    }

    public String getExerciseName() {
        return ename;
    }

    public void setExerciseName(String mExerciseName) {
        this.ename = mExerciseName;
    }


    public static List<Workout> parseCourseJson(String mealJson) throws JSONException {
        List<Workout> workoutList = new ArrayList<>();
        if(mealJson != null){

            JSONArray arr = new JSONArray(mealJson);
            for(int i = 0; i < arr.length(); i++){
                JSONObject obj = arr.getJSONObject(i);
                Workout workout = new Workout(obj.getString("ename"),obj.getString("email"));
                workoutList.add(workout);
            }

        }
        return workoutList;
    }
}
