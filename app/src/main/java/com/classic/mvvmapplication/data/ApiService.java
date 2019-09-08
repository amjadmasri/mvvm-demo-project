package com.classic.mvvmapplication.data;

import com.classic.mvvmapplication.data.models.api.CreateGuestSessionResponse;
import com.classic.mvvmapplication.data.models.api.CreateUserSessionResponse;
import com.classic.mvvmapplication.data.models.api.LoginResponse;
import com.classic.mvvmapplication.data.models.api.MoviesListResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

}
