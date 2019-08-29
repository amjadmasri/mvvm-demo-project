package com.classic.mvvmapplication.data.local;

import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.models.local.Movie;

import java.util.List;

public interface MovieDbHelper {

    LiveData<List<Movie>> getMovieList();

    void insertMovie(Movie movie);

    void insertMovieList(List<Movie> movieList);
}
