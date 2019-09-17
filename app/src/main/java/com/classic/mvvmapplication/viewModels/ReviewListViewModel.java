package com.classic.mvvmapplication.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.classic.mvvmapplication.data.models.local.ReviewLocal;
import com.classic.mvvmapplication.data.repositories.implementations.AppReviewRepository;
import com.classic.mvvmapplication.utilities.Resource;

import javax.inject.Inject;

public class ReviewListViewModel extends ViewModel {
    private AppReviewRepository appReviewRepository;

    private final MutableLiveData<Resource<PagedList<ReviewLocal>>> pagedReviewList;
    private Integer pageNumber = 1;

    @Inject
    public ReviewListViewModel(AppReviewRepository appReviewRepository) {
        this.appReviewRepository = appReviewRepository;
        pagedReviewList = new MutableLiveData<>();
    }

    public LiveData<Resource<PagedList<ReviewLocal>>> getPagedReviewList(int movieId){
       return appReviewRepository.getPagedRemoteMovieReviewList(movieId,pageNumber);
    }
}
