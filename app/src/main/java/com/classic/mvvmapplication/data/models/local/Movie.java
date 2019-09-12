package com.classic.mvvmapplication.data.models.local;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.classic.mvvmapplication.utilities.GenreConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

@Entity(tableName = "movies", indices = {@Index(value = "id", unique = true)})
public class Movie{

    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private Integer id;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("vote_average")
    @Expose
    private Float voteAverage;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("budget")
    @Expose
    private Integer budget;

    @SerializedName("genres")
    @Expose
    private List<Genre> genres = null;

    @SerializedName("imdb_id")
    @Expose
    private String imdbId;

    @SerializedName("revenue")
    @Expose
    private long revenue;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("tagline")
    @Expose
    private String tagline;

    private boolean hasDetails;

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public boolean isHasDetails() {
        return hasDetails;
    }

    public void setHasDetails(boolean hasDetails) {
        this.hasDetails = hasDetails;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return getRevenue() == movie.getRevenue() &&
                isHasDetails() == movie.isHasDetails() &&
                Objects.equals(getPopularity(), movie.getPopularity()) &&
                Objects.equals(getVoteCount(), movie.getVoteCount()) &&
                Objects.equals(getVideo(), movie.getVideo()) &&
                Objects.equals(getPosterPath(), movie.getPosterPath()) &&
                Objects.equals(getId(), movie.getId()) &&
                Objects.equals(getAdult(), movie.getAdult()) &&
                Objects.equals(getBackdropPath(), movie.getBackdropPath()) &&
                Objects.equals(getOriginalLanguage(), movie.getOriginalLanguage()) &&
                Objects.equals(getOriginalTitle(), movie.getOriginalTitle()) &&
                Objects.equals(getTitle(), movie.getTitle()) &&
                Objects.equals(getVoteAverage(), movie.getVoteAverage()) &&
                Objects.equals(getOverview(), movie.getOverview()) &&
                Objects.equals(getReleaseDate(), movie.getReleaseDate()) &&
                Objects.equals(getBudget(), movie.getBudget()) &&
                Objects.equals(getGenres(), movie.getGenres()) &&
                Objects.equals(getImdbId(), movie.getImdbId()) &&
                Objects.equals(getStatus(), movie.getStatus()) &&
                Objects.equals(getTagline(), movie.getTagline());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPopularity(), getVoteCount(), getVideo(), getPosterPath(), getId(), getAdult(), getBackdropPath(), getOriginalLanguage(), getOriginalTitle(), getTitle(), getVoteAverage(), getOverview(), getReleaseDate(), getBudget(), getGenres(), getImdbId(), getRevenue(), getStatus(), getTagline(), isHasDetails());
    }
}
