package com.classic.mvvmapplication.data.repositories.interfaces;

import com.classic.mvvmapplication.data.api.MovieApiHelper;
import com.classic.mvvmapplication.data.api.UserApiHelper;
import com.classic.mvvmapplication.data.local.MovieDbHelper;
import com.classic.mvvmapplication.data.local.UserDbHelper;

public interface UserRepository extends UserDbHelper, UserApiHelper, DataRepository {
}
