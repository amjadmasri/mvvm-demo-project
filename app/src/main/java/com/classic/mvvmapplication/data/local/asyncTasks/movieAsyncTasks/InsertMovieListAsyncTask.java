package com.classic.mvvmapplication.data.local.asyncTasks.movieAsyncTasks;

import android.os.AsyncTask;

import com.classic.mvvmapplication.data.AppDatabase;
import com.classic.mvvmapplication.data.models.local.Movie;

import java.util.List;

import javax.inject.Inject;


public class InsertMovieListAsyncTask extends AsyncTask<List<Movie>,Void ,Void> {

    private final AppDatabase appDataBase;

    @Inject
    public InsertMovieListAsyncTask(AppDatabase appDatabase){
        this.appDataBase=appDatabase;
    }

    @Override
    protected Void doInBackground(List<Movie>... movies) {
        appDataBase.getMovieDao().insertList(movies[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
