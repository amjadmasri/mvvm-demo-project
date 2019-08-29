package com.classic.mvvmapplication.data;

import com.classic.mvvmapplication.data.models.api.MoviesListResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public interface ApiService {

    @GET("movie/popular")
    Single<Response<List<MoviesListResponse>>> getPopularMovies();
}
