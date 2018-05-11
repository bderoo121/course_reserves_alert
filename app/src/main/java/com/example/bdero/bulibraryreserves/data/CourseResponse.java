package com.example.bdero.bulibraryreserves.data;

import java.util.Arrays;

public class CourseResponse {
    CourseX[] course;
    int total_record_count;

    public CourseX[] getCourse() {
        return course;
    }

    public void setCourse(CourseX[] course) {
        this.course = course;
    }

    public int getTotal_record_count() {
        return total_record_count;
    }

    public void setTotal_record_count(int total_record_count) {
        this.total_record_count = total_record_count;
    }

    @Override
    public String toString() {
        return "CourseResponse{" +
                "course=" + Arrays.toString(course) +
                ", total_record_count=" + total_record_count +
                '}';
    }
}

class CourseX{
    private long id;
    private String code;
    private String name;
    private String section;


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

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", section='" + section + '\'' +
                '}';
    }
}
