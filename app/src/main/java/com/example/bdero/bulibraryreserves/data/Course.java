package com.example.bdero.bulibraryreserves.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bdero on 3/26/2018.
 */

@Entity
public class Course {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "code")
    private String courseCode; // E.g. "MA211"

    @ColumnInfo(name = "name")
    private String courseName; // E.g. "Calculus I"

    @ColumnInfo(name = "course_instructors")
    private ArrayList<String[]> courseInstructors;

    @ColumnInfo(name = "link")
    private String courseLink;
    // E.g. "https://api-na.hosted.exlibrisgroup.com/almaws/v1/courses/3700926720001161"

    @ColumnInfo(name = "reading_list_links")
    private ArrayList<String> readingListLinks;


    private ArrayList<JSONObject> mCitations;


    private boolean mIsActive;
    private boolean mAreCitationsLoaded;

    public Course(JSONObject courseJSON) {
        try {
            this.courseCode = courseJSON.getString("code");
            this.courseName = courseJSON.getString("name");
            this.courseLink = courseJSON.getString("link");
            //this.mReadingListsLink = courseLink.buildUpon().appendPath("reading-lists").build();


            this.courseInstructors = new ArrayList<>();
            JSONArray jsonInstructors = courseJSON.getJSONArray("instructor");
            for (int i = 0; i < jsonInstructors.length(); i++) {
                JSONObject instructor = jsonInstructors.getJSONObject(i);
                String[] instructorStrings = {
                        instructor.getString("primary_id"),
                        instructor.getString("first_name"),
                        instructor.getString("last_name")
                };
                this.courseInstructors.add(instructorStrings);
            }

            this.readingListLinks = new ArrayList<>();

            this.mCitations = new ArrayList<>();

            this.mIsActive = courseJSON.getString("status").equals("ACTIVE");
            this.mAreCitationsLoaded = courseJSON.optBoolean("citations_loaded");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getCode() { return courseCode; }
    public void setCode(String value) {courseCode = value;}

    public String getName() { return courseName; }
    public void setName(String name) {courseName = name;}

    public String getCourseLink() { return courseLink; }
    public void setCourseLink(String link) {courseLink = link;}

    public ArrayList<String[]> getInstructors() { return courseInstructors; }
    public void setInstructors(ArrayList<String[]> instructors) {courseInstructors = instructors;}
    public void addInstructor(String[] instructor) {courseInstructors.add(instructor);}

    public ArrayList<String> getReadingLists() { return readingListLinks; }
    public void setReadingLists(ArrayList<String> readingLists) { readingListLinks = readingLists; }
    public void addReadingList(String readingListLink) { readingListLinks.add(readingListLink); }

    public ArrayList<JSONObject> getCitations(){ return mCitations; }
    public void addCitation(JSONObject citation) { mCitations.add(citation); }

}
