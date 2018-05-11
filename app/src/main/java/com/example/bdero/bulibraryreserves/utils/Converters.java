package com.example.bdero.bulibraryreserves.utils;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {

    @TypeConverter
    public static ArrayList<String> toStringArrayList(String value){
        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        return new Gson().fromJson(value,listType);
    }

    @TypeConverter
    public static String fromStringArrayList(ArrayList<String> list){
        return new Gson().toJson(list);
    }

    @TypeConverter
    public static ArrayList<String[]> toArrayStringArrayList(String value){
        Type listType = new TypeToken<ArrayList<String[]>>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayStringArrayList(ArrayList<String[]> list){
        return new Gson().toJson(list);
    }
}