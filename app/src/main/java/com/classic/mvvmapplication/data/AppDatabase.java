package com.classic.mvvmapplication.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.classic.mvvmapplication.data.dao.CountryDao;
import com.classic.mvvmapplication.data.models.local.Country;

@Database(entities = {Country.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CountryDao getCountryDao();
}
