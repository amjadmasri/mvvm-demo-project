package com.classic.mvvmapplication.data.local;

import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.AppDatabase;
import com.classic.mvvmapplication.data.models.local.User;

import javax.inject.Inject;

import io.reactivex.Completable;

public class AppUserDbHelper implements UserDbHelper {

    private AppDatabase appDatabase;

    @Inject
    public AppUserDbHelper(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;

    }

    @Override
    public LiveData<User> getUser() {
        return appDatabase.getUserProfileDao().loadUser();
    }

    @Override
    public void saveUser(User user) {
        appDatabase.getUserProfileDao().insertUser(user);
    }

    @Override
    public Completable deleteUser(Integer userId) {
        return appDatabase.getUserProfileDao().deleteUser(userId);
    }
}
