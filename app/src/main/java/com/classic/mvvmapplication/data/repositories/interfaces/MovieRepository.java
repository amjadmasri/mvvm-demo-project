package com.classic.mvvmapplication.data.repositories.interfaces;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.classic.mvvmapplication.data.api.MovieApiHelper;
import com.classic.mvvmapplication.data.local.MovieDbHelper;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.utilities.Resource;

import java.util.List;

public interface MovieRepository extends DataRepository {

    LiveData<Resource<List<Movie>>> getRemotePopularMovieList(int page);

    LiveData<Resource<PagedList<Movie>>> getPagedRemotePopularMovieList(int page);

    LiveData<List<Movie>> getAllLocalMovieList();

    LiveData<Resource<Movie>> insertMovie(Movie movie);

    LiveData<Resource<Movie>> insertMovieList(List<Movie> movieList);

    LiveData<Resource<String>> rateMovie(Movie movie,float rating);
}
