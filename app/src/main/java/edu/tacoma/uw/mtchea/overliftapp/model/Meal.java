package edu.tacoma.uw.mtchea.overliftapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Kucheryavyy
 * Meal
 */
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

    /**
     *
     * @param s1
     * @param s2
     * @param s3
     * @param s4
     * @param s5
     * @param s6
     * @param s7
     */
    public Meal(int s1, int s2, int s3, int s4, String s5, int s6, String s7){

        mFoodName = s5;
        mCalories = s1;
        mFats = s3;
        mCarbs = s2;
        mProteins = s4;
        mQuantity = s6;
        mEmail = s7;

    }

    /**
     * get food
     * @return
     */
    public String getFoodId() {
        return mFoodName;
    }

    /**
     * set food
     * @param mFoodId
     */
    public void setFoodName(String mFoodId) {
        this.mFoodName = mFoodId;
    }

    /**
     * get calories
     * @return
     */
    public int getCalories() {
        return mCalories;
    }

    /**
     * return calories
     * @param mCalories
     */
    public void setCalories(int mCalories) {
        this.mCalories = mCalories;
    }

    /**
     * get fats
     * @return
     */
    public int getFats() {
        return mFats;
    }

    /**
     * set fats
     * @param mFats
     */
    public void setFats(int mFats) {
        this.mFats = mFats;
    }

    /**
     * get carbs
     * @return
     */
    public int getCarbs() {
        return mCarbs;
    }

    /**
     * set carbs
     * @param mCarbs
     */
    public void setCarbs(int mCarbs) {
        this.mCarbs = mCarbs;
    }

    /**
     * get proteins
     * @return
     */
    public int getProteins() {
        return mProteins;
    }

    /**
     * set proteins
     * @param mProteins
     */
    public void setmProteins(int mProteins) {
        this.mProteins = mProteins;
    }

    /**
     * get quantity
     * @return
     */
    public int getQuantity() {
        return mQuantity;
    }

    /**
     * set quantity
     * @param mQuantity
     */
    public void setQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    /**
     * parse json
     * @param mealJson
     * @return
     * @throws JSONException
     */
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
