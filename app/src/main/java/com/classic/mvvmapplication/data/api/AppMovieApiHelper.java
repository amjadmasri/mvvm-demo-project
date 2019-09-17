package com.classic.mvvmapplication.data.api;

import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.ApiService;
import com.classic.mvvmapplication.data.models.api.MoviesListResponse;
import com.classic.mvvmapplication.data.models.local.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;

public class AppMovieApiHelper implements MovieApiHelper{

    private final ApiService apiService;

    @Inject
    public AppMovieApiHelper(ApiService apiService){
        this.apiService=apiService;
    }
    @Override
    public Single<Response<MoviesListResponse>> getRemotePopularMovieList(int page) {
        return apiService.getPopularMovies(page);
    }

    @Override
    public Single<Response<Movie>> getMovieDetails(int movieId) {
        return apiService.getMovieDetails(movieId);
    }

    @Override
    public Single<Response<MoviesListResponse>> getSimilarMoviesById(int movieId) {
        return apiService.getSimilarMoviesById(movieId);
    }
}
