package com.classic.mvvmapplication.data.repositories.implementations;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.api.UserApiHelper;
import com.classic.mvvmapplication.data.local.UserDbHelper;
import com.classic.mvvmapplication.data.models.local.User;
import com.classic.mvvmapplication.data.prefs.PreferencesHelper;
import com.classic.mvvmapplication.data.repositories.interfaces.UserRepository;

import javax.inject.Inject;

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
}
