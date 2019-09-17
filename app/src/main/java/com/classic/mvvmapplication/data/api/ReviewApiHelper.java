package com.classic.mvvmapplication.data.api;

import androidx.annotation.Nullable;

import com.classic.mvvmapplication.data.models.api.ReviewListResponse;

import io.reactivex.Single;
import retrofit2.Response;

public interface ReviewApiHelper {

    Single<Response<ReviewListResponse>> getMovieReviews(int movieId,int page);
}
