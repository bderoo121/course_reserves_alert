package com.example.bdero.bulibraryreserves;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bdero on 3/26/2018.
 */

public class Course {

    private String mCourseCode; // E.g. "MA211"
    private String mCourseName; // E.g. "Calculus I"
    private String mCourseLink;
    // E.g. "https://api-na.hosted.exlibrisgroup.com/almaws/v1/courses/3700926720001161"

    private ArrayList<String[]> mInstructors;
    private ArrayList<String> mReadingListLinks;
    private ArrayList<JSONObject> mCitations;
    private boolean mIsActive;
    private boolean mAreCitationsLoaded;

    public Course(JSONObject courseJSON) {
        try {
            this.mCourseCode = courseJSON.getString("code");
            this.mCourseName = courseJSON.getString("name");
            this.mCourseLink = courseJSON.getString("link");
            //this.mReadingListsLink = mCourseLink.buildUpon().appendPath("reading-lists").build();


            this.mInstructors = new ArrayList<>();
            JSONArray instructors = courseJSON.getJSONArray("instructor");
            for (int i = 0; i < instructors.length(); i++) {
                JSONObject instructor = instructors.getJSONObject(i);
                String[] instructorStrings = {
                        instructor.getString("primary_id"),
                        instructor.getString("first_name"),
                        instructor.getString("last_name")
                };
                this.mInstructors.add(instructorStrings);
            }

            this.mReadingListLinks = new ArrayList<>();

            this.mCitations = new ArrayList<>();

            this.mIsActive = courseJSON.getString("status").equals("ACTIVE");
            this.mAreCitationsLoaded = courseJSON.optBoolean("citations_loaded");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getCourseCode() {
        return mCourseCode;
    }

    public String getCourseName() {
        return mCourseName;
    }

    public String getCourseLink() {
        return mCourseLink;
    }

    public ArrayList<String[]> getInstructors() {
        return mInstructors;
    }

    public ArrayList<String> getReadingLists() {
        return mReadingListLinks;
    }

    public void addReadingList(String readingListLink) {
        mReadingListLinks.add(readingListLink);
    }

    public void addCitation(JSONObject citation) {
        mCitations.add(citation);
    }

}
