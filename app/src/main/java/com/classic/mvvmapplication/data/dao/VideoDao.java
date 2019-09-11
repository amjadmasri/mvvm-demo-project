package com.classic.mvvmapplication.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.classic.mvvmapplication.data.models.local.VideoLocal;

import java.util.List;

import io.reactivex.Completable;

public interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(VideoLocal videoLocal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertList(List<VideoLocal> videoLocalList);

    @Query("SELECT * FROM videos")
    LiveData<List<VideoLocal>> loadMovies();

    @Query("SELECT * from videos where rel_type='movie' AND rel_id=:movieId ")
    LiveData<List<VideoLocal>> getVideosByMovieId(int movieId);

    @Query("SELECT * from videos where rel_type='tv' AND rel_id=:tvId ")
    LiveData<List<VideoLocal>> getVideosByTvId(int tvId);
}
