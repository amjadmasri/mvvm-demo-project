package com.classic.mvvmapplication.ui.bindingModels;

import androidx.databinding.ObservableField;

import com.classic.mvvmapplication.data.models.local.Movie;

public class MoviePosterItemBindingModel {

    public final ObservableField<String> moviePosterPath;

    public final ObservableField<String> movieOriginalName;

    public final MovieItemListener movieItemListener;

    private final Movie movie;

    public MoviePosterItemBindingModel(Movie movie, MovieItemListener movieItemListener) {
        this.moviePosterPath = new ObservableField<>(movie.getPosterPath());
        this.movieOriginalName = new ObservableField<>(movie.getTitle());
        this.movieItemListener = movieItemListener;
        this.movie = movie;
    }

    public void onItemClick() {
        movieItemListener.onItemClick(movie.getId());
    }


    public interface MovieItemListener {

        void onItemClick(int movieId);
    }
}
