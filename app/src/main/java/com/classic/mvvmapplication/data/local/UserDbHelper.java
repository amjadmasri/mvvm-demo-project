package com.classic.mvvmapplication.data.local;

import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.models.local.User;

import io.reactivex.Completable;

public interface UserDbHelper {

    LiveData<User> getUser();

    void saveUser(User user);

    Completable deleteUser(Integer userId);
}
