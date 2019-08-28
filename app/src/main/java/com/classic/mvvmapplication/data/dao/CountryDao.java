package com.classic.mvvmapplication.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.classic.mvvmapplication.data.models.local.Country;

import java.util.List;

@Dao
public interface CountryDao {

    @Query("SELECT * FROM countries")
    LiveData<List<Country>> loadCountries();

}
