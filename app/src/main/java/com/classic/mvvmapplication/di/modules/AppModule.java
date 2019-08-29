package com.classic.mvvmapplication.di.modules;


import android.content.Context;

import androidx.room.Room;

import com.classic.mvvmapplication.data.AppDatabase;
import com.classic.mvvmapplication.di.interfaces.ApiKeyInfo;
import com.classic.mvvmapplication.di.interfaces.DatabaseInfo;
import com.classic.mvvmapplication.utilities.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @ApiKeyInfo
    String provideAPIKey() {
        return AppConstants.API_KEY;
    }



    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration()
                .build();
    }

}
