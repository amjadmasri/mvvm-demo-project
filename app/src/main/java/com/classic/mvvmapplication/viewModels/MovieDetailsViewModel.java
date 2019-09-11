package com.classic.mvvmapplication.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.data.models.local.VideoLocal;
import com.classic.mvvmapplication.data.repositories.implementations.AppMovieRepository;
import com.classic.mvvmapplication.data.repositories.implementations.AppVideoRepository;
import com.classic.mvvmapplication.utilities.Resource;

import java.util.List;

import javax.inject.Inject;

public class MovieDetailsViewModel extends ViewModel {

    private LiveData<Resource<List<VideoLocal>>> videoList ;
    private LiveData<Resource<Movie>> movieDetails;

    private AppVideoRepository appVideoRepository;
    private AppMovieRepository appMovieRepository;

    @Inject
    public MovieDetailsViewModel(AppVideoRepository appVideoRepository, AppMovieRepository appMovieRepository) {
        this.appVideoRepository = appVideoRepository;
        this.appMovieRepository = appMovieRepository;
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
}
