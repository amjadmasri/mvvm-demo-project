package com.classic.mvvmapplication.data.api;

import com.classic.mvvmapplication.data.models.api.VideoResponse;

import io.reactivex.Single;
import retrofit2.Response;

public interface VideoApiHelper {

    Single<Response<VideoResponse>> getMovieVideos(int movieId);
    Single<Response<VideoResponse>> getTvVideos(int videoId);
}
