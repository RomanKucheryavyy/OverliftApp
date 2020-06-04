package edu.tacoma.uw.mtchea.overliftapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Meal implements Serializable {
    private String mFoodName;
    private String mCalories;
    private String mFats;
    private String mCarbs;
    private String mProteins;
    private String mQuantity;

    public static final String NAME = "foodname";
    public static final String CALORIES = "calories";
    public static final String FATS = "fats";
    public static final String CARBS = "carbs";
    public static final String PROTEINS = "proteins";
    public static final String QUANTITY = "quantity";

    public Meal(String s1, String s2, String s3, String s4, String s5, String s6){

        mFoodName = s1;
        mCalories = s2;
        mFats = s3;
        mCarbs = s4;
        mProteins = s5;
        mQuantity = s6;

    }

    public String getFoodId() {
        return mFoodName;
    }

    public void setFoodName(String mFoodId) {
        this.mFoodName = mFoodId;
    }

    public String getCalories() {
        return mCalories;
    }

    public void setCalories(String mCalories) {
        this.mCalories = mCalories;
    }

    public String getFats() {
        return mFats;
    }

    public void setFats(String mFats) {
        this.mFats = mFats;
    }

    public String getCarbs() {
        return mCarbs;
    }
    public void setCarbs(String mCarbs) {
        this.mCarbs = mCarbs;
    }

    public String getProteins() {
        return mProteins;
    }
    public void setmProteins(String mProteins) {
        this.mProteins = mProteins;
    }

    public String getQuantity() {
        return mQuantity;
    }
    public void setQuantity(String mQuantity) {
        this.mQuantity = mQuantity;
    }

    public static List<Meal> parseCourseJson(String courseJson) throws JSONException {
        List<Meal> mealList = new ArrayList<>();
        if(courseJson != null){

            JSONArray arr = new JSONArray(courseJson);

            for(int i = 0; i < arr.length(); i++){
                JSONObject obj = arr.getJSONObject(i);
                Meal meal = new Meal(obj.getString(Meal.NAME), obj.getString(Meal.CALORIES),
                        obj.getString(Meal.CARBS), obj.getString(Meal.FATS),obj.getString(Meal.PROTEINS),obj.getString(Meal.QUANTITY));
                mealList.add(meal);

            }

        }
        return mealList;
    }
}
