package com.classic.mvvmapplication.data.local;

import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.AppDatabase;
import com.classic.mvvmapplication.data.local.asyncTasks.movieAsyncTasks.InsertMovieAsyncTask;
import com.classic.mvvmapplication.data.local.asyncTasks.movieAsyncTasks.InsertMovieListAsyncTask;
import com.classic.mvvmapplication.data.models.local.Movie;

import java.util.List;

import javax.inject.Inject;

public class AppMovieDbHelper implements MovieDbHelper {

    private final InsertMovieAsyncTask insertMovieAsyncTask;
    private final InsertMovieListAsyncTask insertMovieListAsyncTask;
    private AppDatabase appDatabase;

    @Inject
    public AppMovieDbHelper(AppDatabase appDatabase, InsertMovieAsyncTask insertMovieAsyncTask, InsertMovieListAsyncTask insertMovieListAsyncTask) {
        this.appDatabase = appDatabase;
        this.insertMovieAsyncTask = insertMovieAsyncTask;
        this.insertMovieListAsyncTask=insertMovieListAsyncTask;
    }

    @Override
    public LiveData<List<Movie>> getMovieList() {
        return appDatabase.getMovieDao().loadMovies();
    }

    @Override
    public void insertMovie(Movie movie) {
        insertMovieAsyncTask.execute(movie);
    }

    @Override
    public void insertMovieList(List<Movie> movieList) {
        insertMovieListAsyncTask.execute(movieList);
    }
}
