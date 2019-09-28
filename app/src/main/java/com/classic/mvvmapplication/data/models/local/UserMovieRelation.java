package com.classic.mvvmapplication.data.models.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userMovieRelations")
public class UserMovieRelation {

    @PrimaryKey(autoGenerate = true)
    private int id ;

    private int userId;

    private String movieId;

    private boolean favorite;

    private boolean watchList;

    private float rating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isWatchList() {
        return watchList;
    }

    public void setWatchList(boolean watchList) {
        this.watchList = watchList;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
