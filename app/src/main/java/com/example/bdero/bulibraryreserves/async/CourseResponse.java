package com.example.bdero.bulibraryreserves.async;

import com.google.gson.annotations.SerializedName;

import com.example.bdero.bulibraryreserves.async.CitationResponse.Citation;

import java.util.ArrayList;
import java.util.Arrays;

public class CourseResponse {
    @SerializedName("course")
    private Course[] courses;

    @SerializedName("total_record_count")
    private int count;

    private boolean isExpanded;

    public Course[] getCourses() {
        return courses;
    }

    public void setCourses(Course[] courses) {
        this.courses = courses;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    @Override
    public String toString() {
        return "CourseResponse{" +
                " record_count=" + count +
                ", is_expanded=" + isExpanded +
                ", courses=" + Arrays.toString(courses) +
                '}';
    }

    public class Course {
        private long id;
        private String code;
        private String name;
        private String section;
        private String status;
        @SerializedName("instructor")
        private Instructor[] instructors;
        @SerializedName("link")
        private String courseLink;
        private ArrayList<String> rlLinks;
        private ArrayList<Citation> citations;
        private boolean citationsLoaded;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Instructor[] getInstructors() {
            return instructors;
        }

        public void setInstructors(Instructor[] instructors) {
            this.instructors = instructors;
        }

        public String getCourseLink() {
            return courseLink;
        }

        public void setCourseLink(String courseLink) {
            this.courseLink = courseLink;
        }

        public ArrayList<String> getRlLinks() {
            return rlLinks;
        }

        public void setRLLinks(ArrayList<String> rlLinks) {
            this.rlLinks = rlLinks;
        }

        public void addRLLink(String link){
            this.rlLinks.add(link);
        }

        public ArrayList<Citation> getCitations(){
            return citations;
        }

        public void setCitations(ArrayList<Citation> values){
            citations = values;
        }

        public void addCitations(Citation... values){
            citations.addAll(Arrays.asList(values));
        }

        public boolean areCitationsLoaded() {
            return citationsLoaded;
        }

        public void setCitationsLoaded(boolean citationsLoaded) {
            this.citationsLoaded = citationsLoaded;
        }

        @Override
        public String toString() {
            return "Course{" +
                    "id=" + id +
                    ", code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    ", section='" + section + '\'' +
                    ", status='" + status + '\'' +
                    ", courseLink='" + courseLink + '\'' +
                    ", rlLinks=" + rlLinks +
                    ", citations=" + citations +
                    ", citations loaded=" + citationsLoaded +
                    '}';
        }
    }

    public class Instructor {
        String first_name;
        String last_name;

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        @Override
        public String toString() {
            return first_name + ' ' + last_name;
        }
    }
}
