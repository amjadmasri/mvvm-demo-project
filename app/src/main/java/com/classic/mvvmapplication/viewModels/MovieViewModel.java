package com.classic.mvvmapplication.viewModels;


import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.data.repositories.interfaces.MovieRepository;
import com.classic.mvvmapplication.utilities.Resource;

import java.util.List;

import javax.inject.Inject;

public class MovieViewModel extends BaseViewModel {

    private final LiveData<Resource<PagedList<Movie>>> pagedMovieList;
    private LiveData<Resource<List<Movie>>> movieListLiveData;
    private MovieRepository movieRepository;

    private Integer pageNumber = 1;

    @Inject
    public MovieViewModel(MovieRepository dataRepository) {
        super(dataRepository);


        movieRepository = (MovieRepository) getDataRepository();

        pagedMovieList = fetchPagedLiveData();
    }

    public LiveData<Resource<PagedList<Movie>>> getPagedMovieList() {
        return pagedMovieList;
    }

    private LiveData<Resource<List<Movie>>> newFetchMovieList() {
        return movieRepository.getRemotePopularMovieList(pageNumber);
    }

    public LiveData<Resource<List<Movie>>> getMovieListLiveData() {
        return movieListLiveData;
    }

    public LiveData<Resource<Movie>> insertMovie(Movie movie) {
        return movieRepository.insertMovie(movie);
    }

    private LiveData<Resource<PagedList<Movie>>> fetchPagedLiveData() {
        return movieRepository.getPagedRemotePopularMovieList(pageNumber);
    }

}
