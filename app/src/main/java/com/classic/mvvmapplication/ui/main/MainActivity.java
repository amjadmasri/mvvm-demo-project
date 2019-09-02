package com.classic.mvvmapplication.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.classic.mvvmapplication.BR;
import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.databinding.ActivityMainBinding;
import com.classic.mvvmapplication.ui.Adapters.MovieAdapter;
import com.classic.mvvmapplication.ui.BaseActivity;
import com.classic.mvvmapplication.utilities.Resource;
import com.classic.mvvmapplication.utilities.ViewModelProviderFactory;
import com.classic.mvvmapplication.viewModels.MovieViewModel;

import java.util.List;
import java.util.Timer;

import javax.inject.Inject;

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
        activityMainBinding.movieRecyclerView.setAdapter(movieAdapter);



        getViewModel().getMovieListLiveData().observe(this, new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(Resource<List<Movie>> movieList) {
                movieAdapter.setData(movieList.data);
            }
        });
    }
}
