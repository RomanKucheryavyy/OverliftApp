package edu.tacoma.uw.mtchea.overliftapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Profile {

    /**
     * User gender
     */
    private String mGender;

    /**
     * User age
     */
    private String mAge;

    /**
     * User height
     */
    private String mHeight;

    /**
     * User weight
     */
    private String mWeight;

    public static final String GENDER = "gender";
    public static final String AGE = "age";
    public static final String HEIGHT = "height";
    public static final String WEIGHT = "weight";

    /**
     * Class constructor
     * @param s1 user gender
     * @param s2 user age
     * @param s3 user height
     * @param s4 user weight
     */
    public Profile(String s1, String s2, String s3, String s4){
        mGender = s1;
        mAge = s2;
        mHeight = s3;
        mWeight = s4;
    }

    /**
     * Gets user's gender
     * @return user gender
     */
    public String getGender() {
        return mGender;
    }

    /**
     * Sets user's gender
     * @param mGender user gender
     */
    public void setGender(String mGender) {
        this.mGender = mGender;
    }

    /**
     * Gets user's age
     * @return user age
     */
    public String getAge() {
        return mAge;
    }

    /**
     * Sets user's age
     * @param mAge user age
     */
    public void setAge(String mAge) {
        this.mAge = mAge;
    }

    /**
     * Gets user's height
     * @return user height
     */
    public String getHeight() {
        return mHeight;
    }

    /**
     * Sets user's height
     * @param mHeight user height
     */
    public void setHeight(String mHeight) {
        this.mHeight = mHeight;
    }

    /**
     * Gets user's weight
     * @return user weight
     */
    public String getWeight() {
        return mWeight;
    }

    /**
     * Sets user's weight
     * @param mWeight user weight
     */
    public void setWeight(String mWeight) {
        this.mWeight = mWeight;
    }

    /**
     * Parse json for profiles
     * @param courseJson json
     * @return a list of profiles
     * @throws JSONException json exception
     */
    public static List<Profile> parseCourseJson(String courseJson) throws JSONException {
        List<Profile> profileList = new ArrayList<>();
        if(courseJson != null){

            JSONArray arr = new JSONArray(courseJson);

            for(int i = 0; i < arr.length(); i++){
                JSONObject obj = arr.getJSONObject(i);
                Profile profile = new Profile(obj.getString(Profile.GENDER), obj.getString(Profile.AGE),
                        obj.getString(Profile.HEIGHT), obj.getString(Profile.WEIGHT));
                profileList.add(profile);

            }

        }
        return profileList;
    }
}
