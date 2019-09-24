package com.classic.mvvmapplication.data;

import com.classic.mvvmapplication.data.models.api.CreateGuestSessionResponse;
import com.classic.mvvmapplication.data.models.api.CreateUserSessionResponse;
import com.classic.mvvmapplication.data.models.api.GenericPostRequestResponse;
import com.classic.mvvmapplication.data.models.api.LoginResponse;
import com.classic.mvvmapplication.data.models.api.MoviesListResponse;
import com.classic.mvvmapplication.data.models.api.ReviewListResponse;
import com.classic.mvvmapplication.data.models.api.VideoResponse;
import com.classic.mvvmapplication.data.models.local.Movie;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/popular")
    Single<Response<MoviesListResponse>> getPopularMovies(@Query("page") int page);

    @GET("authentication/token/new")
    Single<Response<LoginResponse>> getNewRequestToken();

    @GET("authentication/guest_session/new")
    Single<Response<CreateGuestSessionResponse>> createGuestSession();

    @FormUrlEncoded
    @POST("authentication/token/validate_with_login")
    Single<Response<LoginResponse>> validateTokenWithLogin(@Field("username")String userName,@Field("password")String password,@Field("request_token") String requestToken);

    @FormUrlEncoded
    @POST("authentication/session/new")
    Single<Response<CreateUserSessionResponse>> createUserSession(@Field("request_token")String requestToken);


    @GET("movie/{movie_id}/videos")
    Single<Response<VideoResponse>> getMovieVideos(@Path("movie_id")int movieId);

    @GET("tv/{tv_id}/videos")
    Single<Response<VideoResponse>> getTVVideos(@Path("tv_id")int tv_id);

    @GET("movie/{movie_id}")
    Single<Response<Movie>> getMovieDetails(@Path("movie_id") int movieId);


    @GET("movie/{movie_id}/reviews")
    Single<Response<ReviewListResponse>> getMovieReviews(@Path("movie_id")int movieId,@Query("page") int page);

    @GET("movie/{movie_id}/similar")
    Single<Response<MoviesListResponse>> getSimilarMoviesById(@Path("movie_id") int movieId);

    @FormUrlEncoded
    @POST("movie/{movie_id}/rating")
    Single<Response<GenericPostRequestResponse>> rateMovie(@Path("movie_id")int movieId,@Field("value") float rating, @Query("session_id") String sessionId, @Query("guest_session_id") String guestSessionId);
}
