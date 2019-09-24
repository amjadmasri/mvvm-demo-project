package com.classic.mvvmapplication.data.prefs;

public interface PreferencesHelper {

    String getDataLanguage();

    void setDataLanguage(String dataLanguage);

    void setSessionKey(String session);

    void setIsGuest(boolean isGuest);

    boolean isGuest();

    String getSessionKey();

    boolean getIsDataDirty();

    void setIsDirty(boolean isDirty);
}
