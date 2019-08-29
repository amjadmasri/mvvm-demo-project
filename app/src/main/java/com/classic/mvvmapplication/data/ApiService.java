package com.classic.mvvmapplication.data;

import com.classic.mvvmapplication.data.models.api.MoviesListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("movie/popular")
    Call<List<MoviesListResponse>> getPopularMovies();
}
