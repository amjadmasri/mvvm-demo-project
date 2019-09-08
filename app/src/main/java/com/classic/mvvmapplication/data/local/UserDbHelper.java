package com.classic.mvvmapplication.data.local;

import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.models.local.User;

public interface UserDbHelper {

    LiveData<User> getUser();

    void saveUser(User user);
}
