package com.classic.mvvmapplication.viewModels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.classic.mvvmapplication.data.repositories.interfaces.MovieRepository;
import com.classic.mvvmapplication.data.models.api.MoviesListResponse;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.utilities.ApiErrorMessagesProvider;
import com.classic.mvvmapplication.utilities.NetworkBoundPagedResource;
import com.classic.mvvmapplication.utilities.NetworkBoundResource;
import com.classic.mvvmapplication.utilities.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MovieViewModel extends BaseViewModel {

    private LiveData<Resource<List<Movie>>> movieListLiveData ;
    private final LiveData<Resource<PagedList<Movie>>> pagedMovieList;
    private MovieRepository movieRepository;
    private ApiErrorMessagesProvider apiErrorMessagesProvider;
    private Integer pageNumber=1;

    @Inject
    public MovieViewModel(MovieRepository dataRepository, ApiErrorMessagesProvider apiErrorMessagesProvider) {
        super(dataRepository);

        this.apiErrorMessagesProvider =apiErrorMessagesProvider;
        movieRepository =(MovieRepository) getDataRepository();
        //movieListLiveData=newFetchMovieList();

        //pagedMovieList=getPagedMovieList();

        pagedMovieList=fetchPagedLiveData();
    }

    public LiveData<Resource<PagedList<Movie>>> getPagedMovieList(){
        return pagedMovieList;
    }

    private LiveData<Resource<List<Movie>>> newFetchMovieList() {
        return( new NetworkBoundResource<List<Movie>, MoviesListResponse>(apiErrorMessagesProvider) {
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
                return movieRepository.getRemotePopularMovieList(1);
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

   private LiveData<Resource<PagedList<Movie>>> fetchPagedLiveData(){
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
                movieRepository.insertMovieList(item.getResults());
            }

            @NonNull
            @Override
            protected DataSource.Factory<Integer, Movie> loadFromDb() {
                return movieRepository.getPagedPopularMovies();
            }

            @NonNull
            @Override
            protected Single<Response<MoviesListResponse>> createCall(int pageNumber) {
                return movieRepository.getRemotePopularMovieList(pageNumber);
            }
        }).getAsLiveData();
   }

   private void fetchPopularMoviesFromNetwork(Integer page){
        if(page==null)
            page=1;

         movieRepository.getRemotePopularMovieList(page).subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new SingleObserver<Response<MoviesListResponse>>() {
                     @Override
                     public void onSubscribe(Disposable d) {

                     }

                     @Override
                     public void onSuccess(Response<MoviesListResponse> moviesListResponseResponse) {
                        if(moviesListResponseResponse.isSuccessful()){
                            MoviesListResponse moviesListResponse = (MoviesListResponse) moviesListResponseResponse.body();

                            MovieViewModel.this.pageNumber=moviesListResponse.getPage()+1;

                            movieRepository.insertMovieList(moviesListResponse.getResults());
                        }
                     }

                     @Override
                     public void onError(Throwable e) {

                     }
                 });;
   }

   public LiveData<PagedList<Movie>> getPagedMovieListOld(){

       PagedList.Config myPagingConfig = new PagedList.Config.Builder()
               .setPageSize(20)
               .setPrefetchDistance(20)
               .setEnablePlaceholders(true)
               .build();

       DataSource.Factory<Integer, Movie> pagedPopularMovies =
               movieRepository.getPagedPopularMovies();

       return new LivePagedListBuilder<>(pagedPopularMovies, myPagingConfig)
               .setBoundaryCallback(new PagedList.BoundaryCallback<Movie>() {
                   @Override
                   public void onItemAtEndLoaded(@NonNull Movie itemAtEnd) {
                       super.onItemAtEndLoaded(itemAtEnd);
                        fetchPopularMoviesFromNetwork(pageNumber);
                   }

                   @Override
                   public void onZeroItemsLoaded() {
                       fetchPopularMoviesFromNetwork(1);
                   }
               })
               .setInitialLoadKey(1)
                       .build();
   }
}
