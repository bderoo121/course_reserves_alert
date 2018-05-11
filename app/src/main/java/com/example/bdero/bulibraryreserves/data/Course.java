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

    public Course(){}

    @PrimaryKey(autoGenerate = true)
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

    @ColumnInfo(name = "is_active")
    private boolean isActive;

    private ArrayList<String> mCitations;


    private boolean areCitationsLoaded;

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

            this.isActive = courseJSON.getString("status").equals("ACTIVE");
            this.areCitationsLoaded = courseJSON.optBoolean("citations_loaded");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getCourseCode() {
        return this.courseCode;
    }
    public void setCourseCode(String value) {
        this.courseCode = value;
    }

    public String getCourseName() {
        return this.courseName;
    }
    public void setCourseName(String name) {
        this.courseName = name;
    }

    public String getCourseLink() {
        return this.courseLink;
    }
    public void setCourseLink(String link) {
        this.courseLink = link;
    }

    public ArrayList<String[]> getCourseInstructors() {
        return this.courseInstructors;
    }
    public void setCourseInstructors(ArrayList<String[]> instructors) {
        this.courseInstructors = instructors;
    }
    public void addInstructor(String[] instructor) {
        this.courseInstructors.add(instructor);
    }

    public ArrayList<String> getReadingListLinks() {
        return this.readingListLinks;
    }
    public void setReadingListLinks(ArrayList<String> readingLists) {
        this.readingListLinks = readingLists;
    }
    public void addReadingList(String readingListLink) {
        this.readingListLinks.add(readingListLink);
    }

    public boolean getIsActive() {
        return this.isActive;
    }
    public void setIsActive(boolean value) {
        this.isActive = value;
    }

    public ArrayList<String> getCitations(){
        return mCitations;
    }
    public void setCitations(ArrayList<String> list){
        mCitations = list;
    }
    public void addCitation(String citation) {
        mCitations.add(citation);
    }

    public boolean getAreCitationsLoaded() {
        return this.areCitationsLoaded;
    }
    public void setAreCitationsLoaded(boolean value) {
        this.areCitationsLoaded = value;
    }

}
