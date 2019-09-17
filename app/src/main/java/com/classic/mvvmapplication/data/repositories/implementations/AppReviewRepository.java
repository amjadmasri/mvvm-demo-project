package com.classic.mvvmapplication.data.repositories.implementations;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.classic.mvvmapplication.data.api.ReviewApiHelper;
import com.classic.mvvmapplication.data.local.ReviewDBHelper;
import com.classic.mvvmapplication.data.models.api.ReviewListResponse;
import com.classic.mvvmapplication.data.models.api.ReviewRemote;
import com.classic.mvvmapplication.data.models.local.ReviewLocal;
import com.classic.mvvmapplication.data.repositories.interfaces.ReviewRepository;
import com.classic.mvvmapplication.utilities.ApiErrorMessagesProvider;
import com.classic.mvvmapplication.utilities.NetworkBoundResource;
import com.classic.mvvmapplication.utilities.Resource;
import com.classic.mvvmapplication.utilities.modelsMappers.ReviewModelMapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class AppReviewRepository implements ReviewRepository {

    ReviewDBHelper reviewDBHelper;
    ReviewApiHelper reviewApiHelper;
    ApiErrorMessagesProvider apiErrorMessagesProvider;

    @Inject
    public AppReviewRepository(ReviewDBHelper reviewDBHelper, ReviewApiHelper reviewApiHelper,ApiErrorMessagesProvider apiErrorMessagesProvider) {
        this.reviewDBHelper = reviewDBHelper;
        this.reviewApiHelper = reviewApiHelper;
        this.apiErrorMessagesProvider = apiErrorMessagesProvider;

    }

    @Override
    public  LiveData<Resource<String>> insertReview(ReviewLocal reviewLocal) {
        final MutableLiveData<Resource<String>> result= new MutableLiveData<>();
        reviewDBHelper.insertReview(reviewLocal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        result.setValue(Resource.success("insert success"));
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.setValue(Resource.error(e.getMessage(),""));
                    }
                });
        return result;
    }

    @Override
    public  LiveData<Resource<String>> insertReviewList(List<ReviewLocal> reviewLocalList) {
        final MutableLiveData<Resource<String>> result= new MutableLiveData<>();
        reviewDBHelper.insertReviewList(reviewLocalList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        result.setValue(Resource.success("insert success"));
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.setValue(Resource.error(e.getMessage(),""));
                    }
                });
        return result;
    }

    @Override
    public LiveData<Resource<List<ReviewLocal>>> getTwoReviewsByMovieId(final int movieId) {
        return (new NetworkBoundResource<List<ReviewLocal>, ReviewListResponse>(apiErrorMessagesProvider) {
            @Override
            protected Completable saveCallResult(@NonNull ReviewListResponse item) {
                List<ReviewLocal> reviewLocals= new ArrayList<>();
                for (ReviewRemote reviewRemote:item.getResults()) {
                    ReviewLocal reviewLocal = ReviewModelMapper.mapRemoteVideoToLocal(reviewRemote,"movie",item.getId());
                    reviewLocals.add(reviewLocal);
                }

                return reviewDBHelper.insertReviewList(reviewLocals);
            }

            @NonNull
            @Override
            protected LiveData<List<ReviewLocal>> loadFromDb() {
                return reviewDBHelper.getTwoReviewsByMovieId(movieId);
            }

            @NonNull
            @Override
            protected Single<Response<ReviewListResponse>> createCall() {
                return reviewApiHelper.getMovieReviews(movieId,1);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<ReviewLocal> data) {
                if(data!=null)
                    return data.isEmpty();
                else
                    return true;
            }
        }).getAsLiveData();
    }

    @Override
    public LiveData<Resource<PagedList<ReviewLocal>>> getPagedRemoteMovieReviewList(int movieId, int page) {
        return null;
    }

    @Override
    public LiveData<ReviewLocal> getReviewById(String reviewId) {
        return reviewDBHelper.getReviewById(reviewId);
    }
}
