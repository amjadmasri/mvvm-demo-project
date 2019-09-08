package com.classic.mvvmapplication.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.classic.mvvmapplication.data.dao.MovieDao;
import com.classic.mvvmapplication.data.dao.UserProfileDao;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.data.models.local.User;

@Database(entities = {Movie.class, User.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDao getMovieDao();
    public abstract UserProfileDao getUserProfileDao();
}
