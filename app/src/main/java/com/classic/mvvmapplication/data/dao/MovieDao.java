package com.classic.mvvmapplication.data.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.classic.mvvmapplication.data.models.local.Movie;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertList(List<Movie> movieList);

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> loadMovies();

    @Query("SELECT * from movies order by popularity DESC")
    DataSource.Factory<Integer, Movie> loadPagedMovies();


}
