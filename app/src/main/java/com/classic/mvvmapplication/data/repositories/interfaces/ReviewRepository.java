package com.classic.mvvmapplication.data.repositories.interfaces;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.classic.mvvmapplication.data.models.local.ReviewLocal;
import com.classic.mvvmapplication.data.models.local.VideoLocal;
import com.classic.mvvmapplication.utilities.Resource;

import java.util.List;

import io.reactivex.Completable;

public interface ReviewRepository {

    LiveData<Resource<String>> insertReview(ReviewLocal reviewLocal);

    LiveData<Resource<String>> insertReviewList(List<ReviewLocal> reviewLocalList);

    LiveData<Resource<List<ReviewLocal>>> getTwoReviewsByMovieId(int movieId);

    LiveData<Resource<PagedList<ReviewLocal>>> getPagedRemoteMovieReviewList(int movieId,int page);
}
