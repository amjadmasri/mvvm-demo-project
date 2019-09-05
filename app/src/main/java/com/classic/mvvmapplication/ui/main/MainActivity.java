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
import com.classic.mvvmapplication.SplashFragment;
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

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends BaseActivity<ActivityMainBinding, MovieViewModel> implements SplashFragment.OnFragmentInteractionListener {

    @Inject
    DispatchingAndroidInjector<Object> androidInjector;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    CompositeDisposable disposable;


    private MovieViewModel movieViewModel;
    private ActivityMainBinding activityMainBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewmodel;
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
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = getViewDataBinding();

    }


    @Override
    protected void onStop() {
        super.onStop();


        disposable.clear();
    }

}
