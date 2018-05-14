package com.example.bdero.bulibraryreserves.db;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

public class CourseResponse {
    @SerializedName("course")
    Course[] courses;

    @SerializedName("total_record_count")
    int record_count;

    public Course[] getCourses() {
        return courses;
    }

    public void setCourses(Course[] courses) {
        this.courses = courses;
    }

    public int getRecord_count() {
        return record_count;
    }

    public void setRecord_count(int record_count) {
        this.record_count = record_count;
    }

    @Override
    public String toString() {
        return "CourseResponse{" +
                "courses=" + Arrays.toString(courses) +
                ", record_count=" + record_count +
                '}';
    }

    class Course {
        private long id;
        private String code;
        private String name;
        private String section;
        private String status;
        private String link;
        private ArrayList<String> rlLinks;

        private Course(){
            rlLinks = new ArrayList<>();
        }

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
