package com.example.bdero.bulibraryreserves.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    long addCourse(Course course);

    @Update
    int updateCourses(Course... courses);

    @Delete
    int deleteCourses(Course... courses);

    @Query("SELECT * FROM course")
    Course[] loadAll();

    @Query("SELECT * FROM course WHERE code LIKE :courseCode")
    Course[] loadCourseWithCode(String courseCode);

    @Query("SELECT * FROM course WHERE id = :id LIMIT 1")
    Course loadCourseById(int id);


}
