package com.classic.mvvmapplication.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.classic.mvvmapplication.BR;
import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.databinding.ActivityMainBinding;
import com.classic.mvvmapplication.ui.Adapters.MovieAdapter;
import com.classic.mvvmapplication.ui.Adapters.MoviePagedAdapter;
import com.classic.mvvmapplication.ui.BaseActivity;
import com.classic.mvvmapplication.utilities.Resource;
import com.classic.mvvmapplication.utilities.ViewModelProviderFactory;
import com.classic.mvvmapplication.viewModels.MovieViewModel;

import java.util.List;
import java.util.Random;
import java.util.Timer;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends BaseActivity<ActivityMainBinding, MovieViewModel> {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    @Inject
    MovieAdapter movieAdapter;
    @Inject
    GridLayoutManager gridLayoutManager;
    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    CompositeDisposable disposable;
    @Inject
    MoviePagedAdapter moviePagedAdapter;

    private MovieViewModel movieViewModel;
    private ActivityMainBinding activityMainBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
       return R.layout.activity_main;
    }

    @Override
    public MovieViewModel getViewModel() {
        movieViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(MovieViewModel.class);
        return movieViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = getViewDataBinding();


        activityMainBinding.movieRecyclerView.setLayoutManager(gridLayoutManager);
        activityMainBinding.movieRecyclerView.setAdapter(moviePagedAdapter);


        /*
        getViewModel().getMovieListLiveData().observe(this, new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(Resource<List<Movie>> movieList) {
                if(movieList.status.equals(Resource.Status.LOADING)){
                    Timber.d("loading ");
                    setLoading(View.VISIBLE);
                }
                else if (movieList.status.equals(Resource.Status.SUCCESS)) {
                    setLoading(View.GONE);
                    movieAdapter.setData(movieList.data);
                }
                else if (movieList.status.equals(Resource.Status.ERROR)){
                    setLoading(View.GONE);
                    Toast.makeText(MainActivity.this, movieList.message, Toast.LENGTH_SHORT).show();
                }
            }
        });
*/
        getViewModel().getPagedMovieList().observe(this, new Observer<Resource<PagedList<Movie>>>() {
            @Override
            public void onChanged(Resource<PagedList<Movie>> movies) {
                moviePagedAdapter.submitList(movies.data);
            }
        });
    }

    private void setLoading(int state){
        activityMainBinding.addressLookingUp.setVisibility(state);
    }

    @Override
    protected void onStop() {
        super.onStop();


        disposable.clear();
    }

}
