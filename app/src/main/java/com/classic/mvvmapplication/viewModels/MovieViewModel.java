package com.classic.mvvmapplication.viewModels;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.DataRepository;
import com.classic.mvvmapplication.data.MovieRepository;
import com.classic.mvvmapplication.data.models.api.MoviesListResponse;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.ui.BaseViewModel;
import com.classic.mvvmapplication.utilities.NetworkBoundResource;
import com.classic.mvvmapplication.utilities.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;

public class MovieViewModel extends BaseViewModel {

    private LiveData<Resource<List<Movie>>> movieListLiveData ;
    private MovieRepository movieRepository;

    @Inject
    public MovieViewModel(MovieRepository dataRepository) {
        super(dataRepository);

        movieRepository =(MovieRepository) getDataRepository();
        movieListLiveData=newFetchMovieList();
    }

    private LiveData<Resource<List<Movie>>> newFetchMovieList() {
        return( new NetworkBoundResource<List<Movie>, MoviesListResponse>() {
            @Override
            protected void saveCallResult(@NonNull MoviesListResponse item) {
                movieRepository.insertMovieList(item.getResults());
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return movieRepository.getMovieList();
            }

            @NonNull
            @Override
            protected Single<Response<MoviesListResponse>> createCall() {
                return movieRepository.getRemotePopularMovieList();
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {
                return data==null||data.isEmpty();
            }
        }).getAsLiveData();
    }

    public LiveData<Resource<List<Movie>>> getMovieListLiveData() {
        return movieListLiveData;
    }

   public Completable insertMovie(Movie movie){
        return movieRepository.insertMovie(movie);
   }
}
