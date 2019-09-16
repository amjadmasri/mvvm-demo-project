package com.classic.mvvmapplication.data.repositories.interfaces;

import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.api.MovieApiHelper;
import com.classic.mvvmapplication.data.api.UserApiHelper;
import com.classic.mvvmapplication.data.local.MovieDbHelper;
import com.classic.mvvmapplication.data.local.UserDbHelper;
import com.classic.mvvmapplication.data.models.local.User;
import com.classic.mvvmapplication.utilities.Resource;

public interface UserRepository extends  DataRepository {

    LiveData<User> getUser();

    void saveUser(User user);

    LiveData<Resource<String>> serverLoginUser(String email, String password,String requestToken);

    LiveData<Resource<String>>  createUserSession(String requestToken);

    LiveData<Resource<String>> createGuestSession();

    LiveData<Resource<String>> getRequestToken();

    void saveSessionToken(String userSession);

    LiveData<Resource<String>> logOutUser();
}
