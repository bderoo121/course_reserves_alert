package com.example.bdero.bulibraryreserves.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.bdero.bulibraryreserves.utils.Converters;

@Database(entities = {CourseEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CourseDao courseDao();
}
