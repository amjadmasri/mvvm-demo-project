package com.classic.mvvmapplication.data.api;

import androidx.annotation.Nullable;

import com.classic.mvvmapplication.data.ApiService;
import com.classic.mvvmapplication.data.models.api.ReviewListResponse;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Response;

public class AppReviewApiHelper implements ReviewApiHelper {

    ApiService apiService;

    @Inject
    public AppReviewApiHelper(ApiService apiService){
        this.apiService=apiService;
    }

    @Override
    public Single<Response<ReviewListResponse>> getMovieReviews(int movieId,  int page) {
        return apiService.getMovieReviews(movieId,page);
    }
}
