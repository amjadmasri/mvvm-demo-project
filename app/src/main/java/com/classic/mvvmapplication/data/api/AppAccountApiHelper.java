package com.classic.mvvmapplication.data.api;

import com.classic.mvvmapplication.data.ApiService;
import com.classic.mvvmapplication.data.models.api.MoviesListResponse;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Response;

public class AppAccountApiHelper implements AccountApiHelper {

    ApiService apiService;
    String userIdStub="1";

    @Inject
    public AppAccountApiHelper(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Single<Response<MoviesListResponse>> getUserFavoriteMovies(String sessionId) {
        return apiService.getUserFavoriteMovies(userIdStub,sessionId);
    }

    @Override
    public Single<Response<MoviesListResponse>> getUserRatedMovies(String sessionId) {
        return apiService.getUserRatedMovies(userIdStub,sessionId);
    }

    @Override
    public Single<Response<MoviesListResponse>> getUserWatchListMovies(String sessionId) {
        return apiService.getUserWatchListMovies(userIdStub,sessionId);
    }

    @Override
    public Single<Response<MoviesListResponse>> getGuestRatedMovies(String sessionId) {
        return apiService.getGuestRatedMovies(sessionId);
    }
}
