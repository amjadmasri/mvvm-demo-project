package com.classic.mvvmapplication.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.classic.mvvmapplication.data.dao.MovieDao;
import com.classic.mvvmapplication.data.dao.ReviewDao;
import com.classic.mvvmapplication.data.dao.UserProfileDao;
import com.classic.mvvmapplication.data.dao.VideoDao;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.data.models.local.ReviewLocal;
import com.classic.mvvmapplication.data.models.local.User;
import com.classic.mvvmapplication.data.models.local.VideoLocal;
import com.classic.mvvmapplication.utilities.GenreConverter;

@Database(entities = {Movie.class, User.class, VideoLocal.class, ReviewLocal.class}, version = 7)
@TypeConverters(value = {GenreConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDao getMovieDao();
    public abstract UserProfileDao getUserProfileDao();

    public abstract VideoDao getVideoDao();

    public abstract ReviewDao getReviewDao();
}
