package com.classic.mvvmapplication.utilities;

import android.os.AsyncTask;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import timber.log.Timber;

public abstract class NetworkBoundPagedResource<ResultType, RequestType> {
    private final MediatorLiveData<Resource<PagedList<ResultType>>> result = new MediatorLiveData<>();
    private final ApiErrorMessagesProvider apiErrorProvider;
    private int pageNumber=1;
    private LiveData<PagedList<ResultType>> dbSource;

    @MainThread
    public NetworkBoundPagedResource(ApiErrorMessagesProvider apiErrorMessagesProvider) {
        this.apiErrorProvider = apiErrorMessagesProvider;
        result.setValue(Resource.<PagedList<ResultType>>loading(null));
        final DataSource.Factory<Integer, ResultType> pagedSource = loadFromDb();
        dbSource = buildLivePagedList(pagedSource);
        result.addSource(dbSource, new Observer<PagedList<ResultType>>() {
            @Override
            public void onChanged(PagedList<ResultType> data) {
                result.removeSource(dbSource);
                if (NetworkBoundPagedResource.this.shouldFetch(data)) {
                    NetworkBoundPagedResource.this.fetchFromNetwork(pageNumber);
                } else {
                    result.addSource(dbSource, new Observer<PagedList<ResultType>>() {
                        @Override
                        public void onChanged(PagedList<ResultType> newData) {
                            result.setValue(Resource.success(newData));
                        }
                    });
                }
            }
        });
    }

    protected LiveData<PagedList<ResultType>> buildLivePagedList(DataSource.Factory<Integer, ResultType> pagedSource){

        return new LivePagedListBuilder<>(pagedSource, getPagedListConfiguration())
                .setBoundaryCallback(new PagedList.BoundaryCallback<ResultType>() {
                    @Override
                    public void onItemAtEndLoaded(@NonNull ResultType itemAtEnd) {
                        super.onItemAtEndLoaded(itemAtEnd);
                        Timber.d("on last item loaded");
                        pageNumber++;
                        fetchFromNetwork(pageNumber);
                    }

                    @Override
                    public void onZeroItemsLoaded() {
                        //fetchFromNetwork(dbSource,pageNumber);
                    }
                })
                .setInitialLoadKey(1)
                .build();
    };

    protected abstract PagedList.Config getPagedListConfiguration();

    private void fetchFromNetwork(int pageNumber) {
        result.removeSource(dbSource);
        createCall(pageNumber).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<RequestType>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<RequestType> response) {
                        if(response.isSuccessful()){
                            saveResultAndReInit(response.body());
                        }
                        else{
                            result.setValue(Resource.<PagedList<ResultType>>error("unseccuss 403 ",null));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.tag("amjadF");
                        Timber.d(e);
                        result.setValue(Resource.<PagedList<ResultType>>error(apiErrorProvider.getCustomErrorMessage(e),null));
                    }
                });
    }

    @MainThread
    private void saveResultAndReInit(final RequestType response) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                result.addSource(dbSource, new Observer<PagedList<ResultType>>() {
                    @Override
                    public void onChanged(PagedList<ResultType> newData) {
                        result.setValue(Resource.success(newData));
                    }
                });
            }
        }.execute();
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected boolean shouldFetch(@Nullable PagedList<ResultType> data) {
        if(data==null)
            return true;
        else
            return data.size() == 0;
    }

    @NonNull
    @MainThread
    protected abstract DataSource.Factory<Integer, ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Single<Response<RequestType>> createCall(int pageNumber);

    @MainThread
    protected void onFetchFailed() {
    }

    public final LiveData<Resource<PagedList<ResultType>>> getAsLiveData() {
        return result;
    }
}
