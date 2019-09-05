package com.classic.mvvmapplication.data.repositories.interfaces;

import com.classic.mvvmapplication.data.api.MovieApiHelper;
import com.classic.mvvmapplication.data.local.MovieDbHelper;

public interface MovieRepository extends MovieApiHelper, MovieDbHelper, DataRepository {
}
