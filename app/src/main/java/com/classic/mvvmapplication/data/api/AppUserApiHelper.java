package com.classic.mvvmapplication.data.api;

import com.classic.mvvmapplication.data.ApiService;
import com.classic.mvvmapplication.data.models.api.CreateGuestSessionResponse;
import com.classic.mvvmapplication.data.models.api.CreateUserSessionResponse;
import com.classic.mvvmapplication.data.models.api.LoginResponse;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Response;

public class AppUserApiHelper implements UserApiHelper {

    private final ApiService apiService;

    @Inject
    public AppUserApiHelper(ApiService apiService) {
        this.apiService=apiService;
    }


    @Override
    public Single<Response<LoginResponse>> serverLoginUser(String email, String password, String requestToken) {
        return apiService.validateTokenWithLogin(email,password,requestToken);
    }

    @Override
    public Single<Response<LoginResponse>> getRequestToken() {
        return apiService.getNewRequestToken();
    }

    @Override
    public Single<Response<CreateUserSessionResponse>> createUserSession(String requestToken) {
        return apiService.createUserSession(requestToken);
    }

    @Override
    public Single<Response<CreateGuestSessionResponse>> createGuestSession() {
        return apiService.createGuestSession();
    }
}
