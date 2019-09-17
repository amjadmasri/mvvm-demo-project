package com.classic.mvvmapplication.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.data.models.local.ReviewLocal;
import com.classic.mvvmapplication.data.repositories.implementations.AppMovieRepository;
import com.classic.mvvmapplication.data.repositories.implementations.AppReviewRepository;
import com.classic.mvvmapplication.utilities.Resource;

import javax.inject.Inject;

public class ReviewDetailsViewModel extends ViewModel {

    LiveData<Resource<Movie>> movieLiveData ;
    LiveData<ReviewLocal> reviewLocalLiveData;

    AppReviewRepository appReviewRepository;
    AppMovieRepository appMovieRepository;

    @Inject
    public ReviewDetailsViewModel(AppReviewRepository appReviewRepository, AppMovieRepository appMovieRepository) {
        this.appReviewRepository = appReviewRepository;
        this.appMovieRepository = appMovieRepository;
    }

    public LiveData<Resource<Movie>> getMovieDetails(int movieId){
        if(movieLiveData==null){
            movieLiveData=appMovieRepository.getMovieDetails(movieId);
        }
        return movieLiveData;
    }

    public LiveData<ReviewLocal> getReviewDetauls(String reviewId){
        if(reviewLocalLiveData==null){
            reviewLocalLiveData=appReviewRepository.getReviewById(reviewId);
        }
        return reviewLocalLiveData;
    }

}
