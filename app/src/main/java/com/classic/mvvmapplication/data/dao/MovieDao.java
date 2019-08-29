package com.classic.mvvmapplication.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.classic.mvvmapplication.data.models.local.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertList(List<Movie> movieList);

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> loadMovies();


}
