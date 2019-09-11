package com.classic.mvvmapplication.data.api;

import com.classic.mvvmapplication.data.ApiService;
import com.classic.mvvmapplication.data.models.api.VideoResponse;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

public class AppVideoApiHelper implements VideoApiHelper {

    private final ApiService apiService;

    @Inject
    public AppVideoApiHelper(ApiService apiService){
        this.apiService=apiService;
    }

    @Override
    public Single<Response<VideoResponse>> getMovieVideos(int movieId) {
        return apiService.getMovieVideos(movieId);
    }

    @Override
    public Single<Response<VideoResponse>> getTvVideos(int videoId) {
        return apiService.getTVVideos(videoId);
    }
}
