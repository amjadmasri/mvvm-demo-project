package com.classic.mvvmapplication.data.prefs;

public interface PreferencesHelper {

    String getDataLanguage();

    void setDataLanguage(String dataLanguage);

    void setSessionKey(String session);

    String getSessionKey();
}
