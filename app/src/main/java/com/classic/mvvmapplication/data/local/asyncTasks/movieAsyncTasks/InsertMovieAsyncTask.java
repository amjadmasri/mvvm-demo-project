package com.classic.mvvmapplication.data.local.asyncTasks.movieAsyncTasks;

import android.os.AsyncTask;

import com.classic.mvvmapplication.data.AppDatabase;
import com.classic.mvvmapplication.data.local.MovieDbHelper;
import com.classic.mvvmapplication.data.models.local.Movie;

import javax.inject.Inject;


public class InsertMovieAsyncTask extends AsyncTask<Movie,Void ,Void> {

    private final AppDatabase appDataBase;

    @Inject
    public InsertMovieAsyncTask(AppDatabase appDatabase){
        this.appDataBase=appDatabase;
    }

    @Override
    protected Void doInBackground(Movie... movies) {
        appDataBase.getMovieDao().insert(movies[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
