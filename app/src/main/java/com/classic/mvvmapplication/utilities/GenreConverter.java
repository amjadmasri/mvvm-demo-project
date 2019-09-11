package com.classic.mvvmapplication.utilities;

import androidx.room.TypeConverter;

import com.classic.mvvmapplication.data.models.local.Genre;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class GenreConverter {

    @TypeConverter
    public static List<Genre> fromString(String value) {
        return new Gson().fromJson(value,new TypeToken<List<Genre>>(){}.getType());
    }

    @TypeConverter
    public static String fromList(List<Genre> genre) {
        return new Gson().toJson(genre);
    }

}
