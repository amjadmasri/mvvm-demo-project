package com.classic.mvvmapplication.data.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.classic.mvvmapplication.data.models.local.ReviewLocal;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface  ReviewDao {

    @Insert(onConflict = (OnConflictStrategy.REPLACE))
    Completable insert(ReviewLocal reviewLocal);

    @Insert(onConflict = (OnConflictStrategy.REPLACE))
    Completable insertList(List<ReviewLocal> reviewLocalList);

    @Query("select * from review where rel_id=:movieId limit 2")
    LiveData<List<ReviewLocal>> getFirstTwoReviews(int movieId);

    @Query("SELECT * from review where rel_id=:movieId order by id DESC")
    DataSource.Factory<Integer, ReviewLocal> loadPagedReviews(int movieId);
}
