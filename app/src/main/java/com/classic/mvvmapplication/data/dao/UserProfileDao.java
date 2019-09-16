package com.classic.mvvmapplication.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.classic.mvvmapplication.data.models.local.User;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface UserProfileDao {

    @Query("SELECT * FROM users limit 1")
    LiveData<User> loadUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Query("DELETE FROM users WHERE id = :userId")
    Completable deleteUser(Integer userId);
}
