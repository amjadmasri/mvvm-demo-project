package com.classic.mvvmapplication.data.repositories.interfaces;

import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.utilities.Resource;

import java.util.List;

public interface AccountRepository {

    LiveData<Resource<List<Movie>>> getUserFavoriteMovies();

    LiveData<Resource<List<Movie>>> getUserRatedMovies();

    LiveData<Resource<List<Movie>>> getUserWatchListMovies();

    LiveData<Resource<List<Movie>>> getGuestRatedMovies();
}
