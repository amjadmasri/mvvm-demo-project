package com.classic.mvvmapplication.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.data.models.local.ReviewLocal;
import com.classic.mvvmapplication.data.models.local.VideoLocal;
import com.classic.mvvmapplication.data.repositories.implementations.AppMovieRepository;
import com.classic.mvvmapplication.data.repositories.implementations.AppReviewRepository;
import com.classic.mvvmapplication.data.repositories.implementations.AppVideoRepository;
import com.classic.mvvmapplication.utilities.Resource;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class MovieDetailsViewModel extends ViewModel {

    private LiveData<Resource<List<VideoLocal>>> videoList ;
    private LiveData<Resource<Movie>> movieDetails;
    private LiveData<Resource<List<ReviewLocal>>> reviewList ;
    private LiveData<Resource<List<Movie>>> similarMoviesList;

    private AppVideoRepository appVideoRepository;
    private AppMovieRepository appMovieRepository;
    private AppReviewRepository appReviewRepository;

    @Inject
    public MovieDetailsViewModel(AppVideoRepository appVideoRepository, AppMovieRepository appMovieRepository, AppReviewRepository appReviewRepository) {
        this.appVideoRepository = appVideoRepository;
        this.appMovieRepository = appMovieRepository;
        this.appReviewRepository = appReviewRepository;
    }

    public LiveData<Resource<List<VideoLocal>>> getMovieVideo(int movieId){
        if(videoList==null) {
            videoList = appVideoRepository.getVideosByMovieId(movieId);
        }

        return videoList;
    }

    public LiveData<Resource<Movie>> getMovieDetails(int movieId){
        if(movieDetails==null) {
            movieDetails = appMovieRepository.getMovieDetails(movieId);
        }
        return movieDetails;
    }

    public LiveData<Resource<List<ReviewLocal>>> getTwoMovieReview(int movieId){
        if(reviewList==null) {
            reviewList = appReviewRepository.getTwoReviewsByMovieId(movieId);
        }
        return reviewList;
    }

    public LiveData<Resource<List<Movie>>> getSimilarMoviesById(int movieId){
        if(similarMoviesList==null) {
            similarMoviesList = appMovieRepository.getSimilarMovies(movieId);
        }

        return similarMoviesList;
    }

    public LiveData<Resource<String>> addMovieRating(int movieId, Float review) {
        Timber.d("rating "+movieDetails.getValue().data);
        return appMovieRepository.rateMovie(movieDetails.getValue().data,review);
    }
}
