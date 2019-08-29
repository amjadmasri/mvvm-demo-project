package com.classic.mvvmapplication.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.classic.mvvmapplication.data.dao.MovieDao;
import com.classic.mvvmapplication.data.models.local.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDao getMovieDao();
}
