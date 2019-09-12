package com.classic.mvvmapplication.utilities;
import android.annotation.SuppressLint;
import android.os.AsyncTask;


import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
    private final ApiErrorMessagesProvider apiErrorProvider;

    @MainThread
    public NetworkBoundResource(ApiErrorMessagesProvider apiErrorMessagesProvider) {
        this.apiErrorProvider = apiErrorMessagesProvider;
        result.setValue(Resource.<ResultType>loading(null));
        final LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType data) {
                result.removeSource(dbSource);
                if (NetworkBoundResource.this.shouldFetch(data)) {
                    NetworkBoundResource.this.fetchFromNetwork(dbSource);
                } else {
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(ResultType newData) {
                            result.setValue(Resource.success(newData));
                        }
                    });
                }
            }
        });
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType newData) {
                result.setValue(Resource.loading(newData));
            }
        });
        createCall().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<RequestType>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<RequestType> response) {
                        if(response.isSuccessful()){
                            result.removeSource(dbSource);
                            saveResultAndReInit(response.body());
                        }
                        else{
                            result.setValue(Resource.<ResultType>error("unseccuss 403 ",null));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.tag("amjadF");
                        Timber.d(e);
                        result.setValue(Resource.<ResultType>error(apiErrorProvider.getCustomErrorMessage(e),null));
                    }
                });
    }

    @SuppressLint("WrongThread")
    @MainThread
    private void saveResultAndReInit(final RequestType response) {

        saveCallResult(response).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Timber.d("insert movie success");
                        result.addSource(loadFromDb(), new Observer<ResultType>() {
                            @Override
                            public void onChanged(ResultType newData) {
                                result.setValue(Resource.success(newData));
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("insert movie error "+e.getLocalizedMessage());
                        result.setValue(Resource.<ResultType>error(e.getMessage(),null));
                    }
                });
    }

    @WorkerThread
    protected abstract Completable saveCallResult(@NonNull RequestType item);

    @MainThread
    protected boolean shouldFetch(@Nullable ResultType data) {
        return data==null;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Single<Response<RequestType>> createCall();

    @MainThread
    protected void onFetchFailed() {
    }

    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return result;
    }
}
