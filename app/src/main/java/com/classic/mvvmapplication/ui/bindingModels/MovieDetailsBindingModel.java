package com.classic.mvvmapplication.ui.bindingModels;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.classic.mvvmapplication.BR;
import com.classic.mvvmapplication.data.models.local.Genre;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.utilities.CurrencyUtils;

import java.util.List;

public class MovieDetailsBindingModel extends BaseObservable {

    private Boolean adult;
    private String backdropPath;
    private String originalLanguage;
    private String originalTitle;
    private Float voteAverage;
    private String overview;
    private String releaseDate;
    private String budget;
    private List<Genre> genres = null;
    private String imdbId;
    private String revenue;
    private String status;
    private String tagline;

    public MovieDetailsBindingModel(Movie movie) {
        adult=movie.getAdult();
        backdropPath=movie.getBackdropPath();
        originalLanguage=movie.getOriginalLanguage();
        originalTitle=movie.getTitle();
        voteAverage=(movie.getVoteAverage()/2);
        overview=movie.getOverview();
        releaseDate=movie.getReleaseDate();
        budget= CurrencyUtils.longToCurrencyString(movie.getBudget());
        genres=movie.getGenres();
        imdbId=movie.getImdbId();
        revenue=CurrencyUtils.longToCurrencyString(movie.getRevenue());
        status=movie.getStatus();
        tagline=movie.getTagline();
    }

    @Bindable
    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }
    @Bindable
    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
        notifyPropertyChanged(com.classic.mvvmapplication.BR.backdropPath);
    }

    @Bindable
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
        notifyPropertyChanged(com.classic.mvvmapplication.BR.originalLanguage);
    }

    @Bindable
    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
        notifyPropertyChanged(com.classic.mvvmapplication.BR.originalTitle);
    }

    @Bindable
    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
        notifyPropertyChanged(com.classic.mvvmapplication.BR.voteAverage);
    }

    @Bindable
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
        notifyPropertyChanged(com.classic.mvvmapplication.BR.overview);
    }

    @Bindable
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        notifyPropertyChanged(com.classic.mvvmapplication.BR.releaseDate);
    }

    @Bindable
    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
        notifyPropertyChanged(com.classic.mvvmapplication.BR.budget);
    }

    @Bindable
    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
        notifyPropertyChanged(com.classic.mvvmapplication.BR.genres);
    }

    @Bindable
    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
        notifyPropertyChanged(com.classic.mvvmapplication.BR.imdbId);
    }

    @Bindable
    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
        notifyPropertyChanged(com.classic.mvvmapplication.BR.revenue);
    }

    @Bindable
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        notifyPropertyChanged(com.classic.mvvmapplication.BR.status);
    }

    @Bindable
    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
        notifyPropertyChanged(com.classic.mvvmapplication.BR.tagline);
    }
}
