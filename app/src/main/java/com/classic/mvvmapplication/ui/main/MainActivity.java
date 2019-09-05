package com.classic.mvvmapplication.ui.main;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.classic.mvvmapplication.BR;
import com.classic.mvvmapplication.ui.fragments.LoginFragment;
import com.classic.mvvmapplication.ui.fragments.PopularMoviesFragment;
import com.classic.mvvmapplication.ui.fragments.ProfileFragment;
import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.ui.fragments.SettingsFragment;
import com.classic.mvvmapplication.SplashFragment;
import com.classic.mvvmapplication.databinding.ActivityMainBinding;
import com.classic.mvvmapplication.ui.BaseActivity;
import com.classic.mvvmapplication.utilities.ViewModelProviderFactory;
import com.classic.mvvmapplication.viewModels.MovieViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends BaseActivity<ActivityMainBinding, MovieViewModel> implements SplashFragment.OnFragmentInteractionListener ,
        PopularMoviesFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener {

    @Inject
    DispatchingAndroidInjector<Object> androidInjector;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    CompositeDisposable disposable;


    private MovieViewModel movieViewModel;
    private ActivityMainBinding activityMainBinding;
    private NavController navController;

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

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        NavigationUI.setupWithNavController(activityMainBinding.bottomNavigationView, navController);

    }


    @Override
    protected void onStop() {
        super.onStop();


        disposable.clear();
    }

    @Override
    public void modifyToolbarAndNavigationVisibilty(boolean isVisible) {
        if (isVisible) {
            getSupportActionBar().show();
            activityMainBinding.bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            getSupportActionBar().hide();
            activityMainBinding.bottomNavigationView.setVisibility(View.GONE);
        }
    }
}
