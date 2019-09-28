package com.classic.mvvmapplication.data.api;

import com.classic.mvvmapplication.data.models.api.MoviesListResponse;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.utilities.Resource;

import io.reactivex.Single;
import retrofit2.Response;

public interface AccountApiHelper {

    Single<Response<MoviesListResponse>> getUserFavoriteMovies(String sessionId);

    Single<Response<MoviesListResponse>> getUserRatedMovies(String sessionId);

    Single<Response<MoviesListResponse>> getUserWatchListMovies(String sessionId);

    Single<Response<MoviesListResponse>> getGuestRatedMovies(String sessionId);
}
