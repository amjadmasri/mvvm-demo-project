package com.classic.mvvmapplication.data;

import com.classic.mvvmapplication.data.api.MovieApiHelper;
import com.classic.mvvmapplication.data.local.MovieDbHelper;
import com.classic.mvvmapplication.data.prefs.PreferencesHelper;

public interface DataManager extends MovieApiHelper, MovieDbHelper, PreferencesHelper {
}
