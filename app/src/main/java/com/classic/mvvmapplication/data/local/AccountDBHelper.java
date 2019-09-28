package com.classic.mvvmapplication.data.local;

import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.data.models.local.UserMovieRelation;

import java.util.List;

import io.reactivex.Completable;

public interface AccountDBHelper {

    Completable insertMovieRelation(UserMovieRelation userMovieRelation);

    LiveData<List<Movie>> getFavoriteMoviesDetailed();

    LiveData<List<Movie>> getWatchListMoviesDetailed();

    LiveData<List<Movie>> getRatedMoviesDetailed();

    Completable updateMovieFavorite(String movieId,boolean isFavorite);

    Completable updateMovieWatchList(String movieId,boolean watchList);

    Completable updateMovieRating(String movieId,float rating);
}
