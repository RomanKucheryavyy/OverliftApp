package edu.tacoma.uw.mtchea.overliftapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private String mCourseId;
    private String mCourseShortDesc;
    private String mCourseLongDesc;
    private String mCoursePrereqs;

    public static final String ID = "id";
    public static final String SHORT_DESC = "shortdesc";
    public static final String LONG_DESC = "longdesc";
    public static final String PRE_REQS = "prereqs";

    public Course(String s1, String s2, String s3, String s4){

        mCourseId = s1;
        mCourseShortDesc = s2;
        mCourseLongDesc = s3;
        mCoursePrereqs = s4;

    }

    public String getCourseId() {
        return mCourseId;
    }

    public void setCourseId(String mCourseId) {
        this.mCourseId = mCourseId;
    }

    public String getCourseShortDesc() {
        return mCourseShortDesc;
    }

    public void setCourseShortDesc(String mCourseShortDesc) {
        this.mCourseShortDesc = mCourseShortDesc;
    }

    public String getCourseLongDesc() {
        return mCourseLongDesc;
    }

    public void setCourseLongDesc(String mCourseLongDesc) {
        this.mCourseLongDesc = mCourseLongDesc;
    }

    public String getCoursePrereqs() {
        return mCoursePrereqs;
    }

    public void setCoursePrereqs(String mCoursePrereqs) {
        this.mCoursePrereqs = mCoursePrereqs;
    }

    public static List<Course> parseCourseJson(String courseJson) throws JSONException {
        List<Course> courseList = new ArrayList<>();
        if(courseJson != null){

            JSONArray arr = new JSONArray(courseJson);

            for(int i = 0; i < arr.length(); i++){
                JSONObject obj = arr.getJSONObject(i);
                Course course = new Course(obj.getString(Course.ID), obj.getString(Course.SHORT_DESC),
                        obj.getString(Course.LONG_DESC), obj.getString(Course.PRE_REQS));
                courseList.add(course);

            }



        }
        return courseList;
    }
}
