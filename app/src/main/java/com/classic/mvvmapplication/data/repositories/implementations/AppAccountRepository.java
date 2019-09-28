package com.classic.mvvmapplication.data.repositories.implementations;

import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.data.repositories.interfaces.AccountRepository;
import com.classic.mvvmapplication.utilities.Resource;

import java.util.List;

import javax.inject.Inject;

public class AppAccountRepository implements AccountRepository {

    @Inject
    public AppAccountRepository() {
    }

    @Override
    public LiveData<Resource<List<Movie>>> getUserFavoriteMovies() {
        return null;
    }

    @Override
    public LiveData<Resource<List<Movie>>> getUserRatedMovies() {
        return null;
    }

    @Override
    public LiveData<Resource<List<Movie>>> getUserWatchListMovies() {
        return null;
    }

    @Override
    public LiveData<Resource<List<Movie>>> getGuestRatedMovies() {
        return null;
    }
}
