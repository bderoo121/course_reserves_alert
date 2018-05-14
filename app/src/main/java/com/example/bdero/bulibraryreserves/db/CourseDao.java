package com.example.bdero.bulibraryreserves.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface CourseDao {
    @Insert
    long addCourse(CourseEntity course);

    @Update
    int updateCourses(CourseEntity... courses);

    @Delete
    int deleteCourses(CourseEntity... courses);

    @Query("SELECT * FROM CourseEntity")
    CourseEntity[] loadAll();

    @Query("SELECT * FROM CourseEntity WHERE code LIKE :courseCode")
    CourseEntity[] loadCourseWithCode(String courseCode);

    @Query("SELECT * FROM CourseEntity WHERE id = :id LIMIT 1")
    CourseEntity loadCourseById(int id);


}
