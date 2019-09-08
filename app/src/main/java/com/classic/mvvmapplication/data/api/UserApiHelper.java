package com.classic.mvvmapplication.data.api;

import com.classic.mvvmapplication.data.models.api.CreateGuestSessionResponse;
import com.classic.mvvmapplication.data.models.api.CreateUserSessionResponse;
import com.classic.mvvmapplication.data.models.api.LoginResponse;

import io.reactivex.Single;
import retrofit2.Response;

public interface UserApiHelper {

    Single<Response<LoginResponse>> serverLoginUser(String email , String password,String requestToken);

    Single<Response<LoginResponse>> getRequestToken();

    Single<Response<CreateUserSessionResponse>> createUserSession(String requestToken);

    Single<Response<CreateGuestSessionResponse>> createGuestSession();
}
