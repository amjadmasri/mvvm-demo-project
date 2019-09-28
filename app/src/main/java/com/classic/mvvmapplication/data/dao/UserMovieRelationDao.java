package com.classic.mvvmapplication.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.data.models.local.UserMovieRelation;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface UserMovieRelationDao {

    @Insert(onConflict = (OnConflictStrategy.REPLACE))
    Completable insert(UserMovieRelation userMovieRelation);

    @Insert(onConflict = (OnConflictStrategy.REPLACE))
    Completable insertList(List<UserMovieRelation> userMovieRelationList);

    @Query("select * from userMovieRelations where movieId=:movieId")
    LiveData<UserMovieRelation> getUserMovieRelation(String movieId);

    @Query("select movieId from userMovieRelations where favorite=1")
    LiveData<List<String>> getFavoriteMovieIds();

    @Query("select movieId from userMovieRelations where watchList=1")
    LiveData<List<String>> getWatchListMovieIds();

    @Query("select movieId from userMovieRelations where rating IS NOT NULL")
    LiveData<List<String>> getRatedMovieIds();

    @Query("select * from movies inner join userMovieRelations on movies.id = userMovieRelations.movieId where userMovieRelations.favorite=1")
    LiveData<List<Movie>> getFavoriteMoviesDetailed();

    @Query("select * from movies inner join userMovieRelations on movies.id = userMovieRelations.movieId where userMovieRelations.watchList=1")
    LiveData<List<Movie>> getWatchListMoviesDetailed();

    @Query("select * from movies inner join userMovieRelations on movies.id = userMovieRelations.movieId where userMovieRelations.rating IS NOT NULL")
    LiveData<List<Movie>> getRatedMoviesDetailed();

    @Query("UPDATE userMovieRelations set favorite=:isFavorite where movieId=:movieId")
    Completable updateMovieFavorite(String movieId,boolean isFavorite);

    @Query("UPDATE userMovieRelations set watchList=:watchList where movieId=:movieId")
    Completable updateMovieWatchList(String movieId,boolean watchList);

    @Query("UPDATE userMovieRelations set rating=:rating where movieId=:movieId")
    Completable updateMovieRating(String movieId,float rating);
}
