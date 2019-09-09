package com.classic.mvvmapplication.data.repositories.implementations;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;

import com.classic.mvvmapplication.data.api.MovieApiHelper;
import com.classic.mvvmapplication.data.local.MovieDbHelper;
import com.classic.mvvmapplication.data.models.api.MoviesListResponse;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.data.prefs.PreferencesHelper;
import com.classic.mvvmapplication.data.repositories.interfaces.MovieRepository;
import com.classic.mvvmapplication.utilities.ApiErrorMessagesProvider;
import com.classic.mvvmapplication.utilities.NetworkBoundPagedResource;
import com.classic.mvvmapplication.utilities.NetworkBoundResource;
import com.classic.mvvmapplication.utilities.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class AppMovieRepository implements MovieRepository {

    private final Context mContext;
    private final MovieDbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final MovieApiHelper mApiHelper;
    private ApiErrorMessagesProvider apiErrorMessagesProvider;

    @Inject
    public AppMovieRepository(Context context, MovieDbHelper dbHelper, PreferencesHelper preferencesHelper, MovieApiHelper apiHelper, ApiErrorMessagesProvider apiErrorMessagesProvider) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
        this.apiErrorMessagesProvider =apiErrorMessagesProvider;
    }

    @Override
    public LiveData<Resource<List<Movie>>> getRemotePopularMovieList(final int page) {
        return( new NetworkBoundResource<List<Movie>, MoviesListResponse>(apiErrorMessagesProvider) {
            @Override
            protected void saveCallResult(@NonNull MoviesListResponse item) {
                insertMovieList(item.getResults());
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return getAllLocalMovieList();
            }

            @NonNull
            @Override
            protected Single<Response<MoviesListResponse>> createCall() {
                return mApiHelper.getRemotePopularMovieList(page);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {
                return data==null||data.isEmpty();
            }
        }).getAsLiveData();
    }

    @Override
    public LiveData<Resource<PagedList<Movie>>> getPagedRemotePopularMovieList(int page) {
        return (new NetworkBoundPagedResource<Movie, MoviesListResponse>(apiErrorMessagesProvider) {
            @Override
            protected PagedList.Config getPagedListConfiguration() {
                return   new PagedList.Config.Builder()
                        .setPageSize(20)
                        .setPrefetchDistance(20)
                        .setEnablePlaceholders(true)
                        .build();
            }

            @Override
            protected void saveCallResult(@NonNull MoviesListResponse item) {
                insertMovieList(item.getResults());
            }

            @NonNull
            @Override
            protected DataSource.Factory<Integer, Movie> loadFromDb() {
                return getPagedPopularMovies();
            }

            @NonNull
            @Override
            protected Single<Response<MoviesListResponse>> createCall(int pageNumber) {
                return mApiHelper.getRemotePopularMovieList(pageNumber);
            }
        }).getAsLiveData();
    }

    @Override
    public LiveData<List<Movie>> getAllLocalMovieList() {
        return mDbHelper.getMovieList();
    }

    @Override
    public LiveData<Resource<Movie>> insertMovie(Movie movie) {
        final MutableLiveData<Resource<Movie>> result= new MutableLiveData<>();
        mDbHelper.insertMovie(movie).subscribe(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                result.setValue(Resource.<Movie>success(null));
            }

            @Override
            public void onError(Throwable e) {
                result.setValue(Resource.<Movie>error(e.getLocalizedMessage(),null));
            }
        });
        return result;
    }


    public LiveData<Resource<Movie>> insertMovieList(List<Movie> movieList) {
        final MutableLiveData<Resource<Movie>> result= new MutableLiveData<>();
        mDbHelper.insertMovieList(movieList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                result.setValue(Resource.<Movie>success(null));
            }

            @Override
            public void onError(Throwable e) {
                result.setValue(Resource.<Movie>error(e.getLocalizedMessage(),null));
            }
        });
        return result;
    }


    public DataSource.Factory<Integer, Movie> getPagedPopularMovies() {
        return mDbHelper.getPagedPopularMovies();
    }
}
