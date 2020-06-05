package edu.tacoma.uw.mtchea.overliftapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Meal implements Serializable {
    private String mFoodName;
    private int mCalories;
    private int mFats;
    private int mCarbs;
    private int mProteins;
    private int mQuantity;
    private String mEmail;

    public static final String NAME = "foodName";
    public static final String CALORIES = "calories";
    public static final String FATS = "fats";
    public static final String CARBS = "carbs";
    public static final String PROTEINS = "proteins";
    public static final String QUANTITY = "quantity";
    public static final String EMAIL = "email";

    public Meal(int s1, int s2, int s3, int s4, String s5, int s6, String s7){

        mFoodName = s5;
        mCalories = s1;
        mFats = s3;
        mCarbs = s2;
        mProteins = s4;
        mQuantity = s6;
        mEmail = s7;

    }

    public String getFoodId() {
        return mFoodName;
    }

    public void setFoodName(String mFoodId) {
        this.mFoodName = mFoodId;
    }

    public int getCalories() {
        return mCalories;
    }

    public void setCalories(int mCalories) {
        this.mCalories = mCalories;
    }

    public int getFats() {
        return mFats;
    }

    public void setFats(int mFats) {
        this.mFats = mFats;
    }

    public int getCarbs() {
        return mCarbs;
    }
    public void setCarbs(int mCarbs) {
        this.mCarbs = mCarbs;
    }

    public int getProteins() {
        return mProteins;
    }
    public void setmProteins(int mProteins) {
        this.mProteins = mProteins;
    }

    public int getQuantity() {
        return mQuantity;
    }
    public void setQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public static List<Meal> parseCourseJson(String mealJson) throws JSONException {
        List<Meal> mealList = new ArrayList<>();
        if(mealJson != null){

            JSONArray arr = new JSONArray(mealJson);
            for(int i = 0; i < arr.length(); i++){
                JSONObject obj = arr.getJSONObject(i);
                Meal meal = new Meal(obj.getInt("calories"), obj.getInt("carbs"), obj.getInt("fat"), obj.getInt("proteins"),
                        obj.getString("foodname"),obj.getInt("quantity"),obj.getString("email"));
                mealList.add(meal);
            }

        }
        return mealList;
    }
}
