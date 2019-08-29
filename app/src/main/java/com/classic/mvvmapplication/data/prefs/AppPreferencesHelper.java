package com.classic.mvvmapplication.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.classic.mvvmapplication.di.interfaces.PreferenceInfo;

import javax.inject.Inject;



public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_DATA_LANGUAGE = "PREF_KEY_DATA_LANGUAGE";


    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getDataLanguage() {
        return mPrefs.getString(PREF_KEY_DATA_LANGUAGE, "en-US");
    }

    @Override
    public void setDataLanguage(String dataLanguage) {
        mPrefs.edit().putString(PREF_KEY_DATA_LANGUAGE, dataLanguage).apply();
    }
}
