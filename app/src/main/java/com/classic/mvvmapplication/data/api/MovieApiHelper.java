package com.classic.mvvmapplication.data.api;

import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.models.api.MoviesListResponse;
import com.classic.mvvmapplication.data.models.local.Movie;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;

public interface MovieApiHelper {

    Single<Response<MoviesListResponse>> getRemotePopularMovieList(int page);

    Single<Response<Movie>> getMovieDetails(int movieId);

    Single<Response<MoviesListResponse>> getSimilarMoviesById(int movieId);

}
