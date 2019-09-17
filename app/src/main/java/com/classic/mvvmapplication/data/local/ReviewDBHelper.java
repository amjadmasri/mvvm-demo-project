package com.classic.mvvmapplication.data.local;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.classic.mvvmapplication.data.models.local.ReviewLocal;

import java.util.List;

import io.reactivex.Completable;

public interface ReviewDBHelper {

    Completable insertReview(ReviewLocal reviewLocal);

    Completable insertReviewList(List<ReviewLocal> reviewLocals);

    LiveData<List<ReviewLocal>> getTwoReviewsByMovieId(int movieId);

    DataSource.Factory<Integer, ReviewLocal> getPagedMovieReviews(int movieId);
}
