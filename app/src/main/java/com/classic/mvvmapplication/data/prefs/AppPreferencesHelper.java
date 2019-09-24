package com.classic.mvvmapplication.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.classic.mvvmapplication.di.interfaces.PreferenceInfo;

import javax.inject.Inject;



public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_DATA_LANGUAGE = "PREF_KEY_DATA_LANGUAGE";
    private static final String PREF_KEY_SESSION_KEY = "PREF_KEY_SESSION_KEY";
    private static final String PREF_KEY_IS_DATA_DIRTY ="PREF_KEY_IS_DATA_DIRTY";
    private static final String PREF_KEY_IS_GUEST="PREF_KEY_IS_GUEST";


    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public String getDataLanguage() {
        //return mPrefs.getString(PREF_KEY_DATA_LANGUAGE, null)==null? sharedPreferences.getString(PREF_KEY_DATA_LANGUAGE,"en-US"):"en-US";
        return mPrefs.getString(PREF_KEY_DATA_LANGUAGE, "en-US");
    }

    @Override
    public void setDataLanguage(String dataLanguage) {
        mPrefs.edit().putString(PREF_KEY_DATA_LANGUAGE, dataLanguage).apply();
    }

    @Override
    public void setSessionKey(String session) {
        mPrefs.edit().putString(PREF_KEY_SESSION_KEY, session).apply();
    }

    @Override
    public String getSessionKey() {
        return mPrefs.getString(PREF_KEY_SESSION_KEY, null);
    }

    @Override
    public boolean getIsDataDirty() {
        return mPrefs.getBoolean(PREF_KEY_IS_DATA_DIRTY,false);
    }

    @Override
    public void setIsDirty(boolean isDirty) {
        mPrefs.edit().putBoolean(PREF_KEY_IS_DATA_DIRTY,isDirty).apply();
    }

    @Override
    public void setIsGuest(boolean isGuest) {
        mPrefs.edit().putBoolean(PREF_KEY_IS_GUEST,isGuest);
    }

    @Override
    public boolean isGuest() {
        return mPrefs.getBoolean(PREF_KEY_IS_GUEST,false);
    }
}
