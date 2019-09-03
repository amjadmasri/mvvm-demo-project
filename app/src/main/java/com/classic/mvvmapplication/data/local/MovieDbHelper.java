package com.classic.mvvmapplication.data.local;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.classic.mvvmapplication.data.models.local.Movie;

import java.util.List;

import io.reactivex.Completable;

public interface MovieDbHelper {

    LiveData<List<Movie>> getMovieList();

    Completable insertMovie(Movie movie);

    void insertMovieList(List<Movie> movieList);

    DataSource.Factory<Integer, Movie> getPagedPopularMovies();
}
