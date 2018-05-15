package com.example.bdero.bulibraryreserves.db;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

public class CourseResponse {
    @SerializedName("course")
    private Course[] courses;

    @SerializedName("total_record_count")
    private int count;

    private ArrayList<String> encounteredCourses = new ArrayList<>();

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

    public ArrayList<String> getEncounteredCourses(){
        return encounteredCourses;
    }

    public void recordCourseCode(String code){
        encounteredCourses.add(code);
    }

    @Override
    public String toString() {
        return "CourseResponse{" +
                "courses=" + Arrays.toString(courses) +
                ", record_count=" + count +
                '}';
    }

    public class Course {
        private long id;
        private String code;
        private String name;
        private String section;
        private String status;
        private String link;
        private ArrayList<String> rlLinks;

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

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
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

        @Override
        public String toString() {
            return "Course{" +
                    "id=" + id +
                    ", code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    ", section='" + section + '\'' +
                    ", status='" + status + '\'' +
                    ", link='" + link + '\'' +
                    ", rlLinks=" + rlLinks +
                    '}';
        }
    }
}
