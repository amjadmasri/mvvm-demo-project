package com.classic.mvvmapplication.data.repositories.implementations;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.api.UserApiHelper;
import com.classic.mvvmapplication.data.local.UserDbHelper;
import com.classic.mvvmapplication.data.models.api.CreateGuestSessionResponse;
import com.classic.mvvmapplication.data.models.api.CreateUserSessionResponse;
import com.classic.mvvmapplication.data.models.api.LoginResponse;
import com.classic.mvvmapplication.data.models.local.User;
import com.classic.mvvmapplication.data.prefs.PreferencesHelper;
import com.classic.mvvmapplication.data.repositories.interfaces.UserRepository;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Response;

public class AppUserRepository implements UserRepository {

    private final Context mContext;
    private final UserDbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final UserApiHelper mApiHelper;

    @Inject
    public AppUserRepository(Context context, UserDbHelper dbHelper, PreferencesHelper preferencesHelper, UserApiHelper apiHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public LiveData<User> getUser() {
        return mDbHelper.getUser();
    }

    @Override
    public Single<Response<LoginResponse>> serverLoginUser(String email, String password,String requestToken) {
        return mApiHelper.serverLoginUser(email,password,requestToken);
    }

    @Override
    public Single<Response<LoginResponse>> getRequestToken() {
        return mApiHelper.getRequestToken();
    }

    @Override
    public Single<Response<CreateUserSessionResponse>> createUserSession(String requestToken) {
        return mApiHelper.createUserSession(requestToken);
    }

    @Override
    public Single<Response<CreateGuestSessionResponse>> createGuestSession() {
        return mApiHelper.createGuestSession();
    }

    @Override
    public void saveSessionToken(String sessionToken){
        mPreferencesHelper.setSessionKey(sessionToken);
    }
}
