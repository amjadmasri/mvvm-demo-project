package com.classic.mvvmapplication.data;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.classic.mvvmapplication.data.api.MovieApiHelper;
import com.classic.mvvmapplication.data.local.MovieDbHelper;
import com.classic.mvvmapplication.data.models.api.MoviesListResponse;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.data.prefs.PreferencesHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;

public class AppMovieRepository implements MovieRepository {

    private final Context mContext;
    private final MovieDbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final MovieApiHelper mApiHelper;

    @Inject
    public AppMovieRepository(Context context, MovieDbHelper dbHelper, PreferencesHelper preferencesHelper, MovieApiHelper apiHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public Single<Response<MoviesListResponse>> getRemotePopularMovieList(int page) {
        return mApiHelper.getRemotePopularMovieList(page);
    }

    @Override
    public LiveData<List<Movie>> getMovieList() {
        return mDbHelper.getMovieList();
    }

    @Override
    public Completable insertMovie(Movie movie) {
       return mDbHelper.insertMovie(movie);
    }

    @Override
    public void insertMovieList(List<Movie> movieList) {
        mDbHelper.insertMovieList(movieList);
    }

    @Override
    public DataSource.Factory<Integer, Movie> getPagedPopularMovies() {
        return mDbHelper.getPagedPopularMovies();
    }
}
