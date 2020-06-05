package edu.tacoma.uw.mtchea.overliftapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Profile {

    /**
     * User name
     */
    private String mName;

    /**
     * User email
     */
    private String mEmail;

    /**
     * User height
     */
    private String mHeight;

    /**
     * User weight
     */
    private String mWeight;

    /**
     * User gender
     */
    private String mGender;

    /**
     * User age
     */
    private String mAge;

    /**
     * User age
     */
    private String mPassword;

    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String HEIGHT = "height";
    public static final String WEIGHT = "bodyweight";
    public static final String AGE = "age";
    public static final String PASSWORD = "password";

    /**
     * Class constructor
     * @param s1 user name
     * @param s2 user email
     * @param s3 user height
     * @param s4 user weight
     * @param s5 user age
     * @param s6 user password
     */
     public Profile(String s1, String s2, String s3, String s4, String s5, String s6){
        mName = s1;
        mEmail = s2;
        mHeight = s3;
        mWeight = s4;
        mAge = s5;
        mPassword = s6;
    }

    /**
     * Gets user's gender
     * @return user gender
     */
    public String getName() {
        return mName;
    }

    /**
     * Sets user's gender
     * @param mName user gender
     */
    public void setName(String mName) {
        this.mName = mName;
    }

    /**
     * Gets user's gender
     * @return user gender
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Sets user's gender
     * @param mEmail user gender
     */
    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
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
     * Gets user's weight
     * @return user weight
     */
    public String getPassword() {
        return mPassword;
    }

    /**
     * Sets user's weight
     * @param mPassword user password
     */
    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
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
                Profile profile = new Profile(obj.getString(Profile.NAME), obj.getString(Profile.EMAIL),
                        obj.getString(Profile.HEIGHT), obj.getString(Profile.WEIGHT),
                        obj.getString(Profile.AGE), obj.getString(Profile.PASSWORD)
                       );
                profileList.add(profile);

            }

        }
        return profileList;
    }


}
