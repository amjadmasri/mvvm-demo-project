package com.classic.mvvmapplication.data.local;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.classic.mvvmapplication.data.AppDatabase;
import com.classic.mvvmapplication.data.local.asyncTasks.movieAsyncTasks.InsertMovieAsyncTask;
import com.classic.mvvmapplication.data.local.asyncTasks.movieAsyncTasks.InsertMovieListAsyncTask;
import com.classic.mvvmapplication.data.models.local.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import timber.log.Timber;

public class AppMovieDbHelper implements MovieDbHelper {

    //private final InsertMovieAsyncTask insertMovieAsyncTask;
    //private final InsertMovieListAsyncTask insertMovieListAsyncTask;
    private AppDatabase appDatabase;

    @Inject
    public AppMovieDbHelper(AppDatabase appDatabase, InsertMovieAsyncTask insertMovieAsyncTask, InsertMovieListAsyncTask insertMovieListAsyncTask) {
        this.appDatabase = appDatabase;
        //this.insertMovieAsyncTask = insertMovieAsyncTask;
        //this.insertMovieListAsyncTask=insertMovieListAsyncTask;
    }

    @Override
    public LiveData<List<Movie>> getMovieList() {
        Timber.d("getAllLocalMovieList: ");
        return appDatabase.getMovieDao().loadMovies();
    }

    @Override
    public Completable insertMovie(Movie movie) {
        /*
        InsertMovieAsyncTask insertMovieAsyncTask = new InsertMovieAsyncTask(appDatabase);
        insertMovieAsyncTask.execute(movie);*/
        return appDatabase.getMovieDao().insert(movie);
    }

    @Override
    public Completable insertMovieList(List<Movie> movieList) {
       return appDatabase.getMovieDao().insertList(movieList);
    }

    @Override
    public DataSource.Factory<Integer, Movie> getPagedPopularMovies() {
        return appDatabase.getMovieDao().loadPagedMovies();
    }

    @Override
    public LiveData<Movie> getMovieById(int movieId) {
        return appDatabase.getMovieDao().getMovieById(movieId);
    }
}
