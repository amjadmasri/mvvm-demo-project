package com.classic.mvvmapplication.data.local;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.classic.mvvmapplication.data.AppDatabase;
import com.classic.mvvmapplication.data.models.local.ReviewLocal;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;

public class AppReviewDBHelper implements ReviewDBHelper {

    private final AppDatabase appDataBase;

    @Inject
    public AppReviewDBHelper(AppDatabase appDatabase){
        this.appDataBase=appDatabase;
    }

    @Override
    public Completable insertReview(ReviewLocal reviewLocal) {
        return appDataBase.getReviewDao().insert(reviewLocal);
    }

    @Override
    public Completable insertReviewList(List<ReviewLocal> reviewLocals) {
        return appDataBase.getReviewDao().insertList(reviewLocals);
    }

    @Override
    public LiveData<List<ReviewLocal>> getTwoReviewsByMovieId(int movieId) {
        return appDataBase.getReviewDao().getFirstTwoReviews(movieId);
    }

    @Override
    public DataSource.Factory<Integer, ReviewLocal> getPagedMovieReviews(int movieId) {
        return appDataBase.getReviewDao().loadPagedReviews(movieId);
    }
}
