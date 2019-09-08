package com.classic.mvvmapplication.di.builder;

import com.classic.mvvmapplication.SplashFragment;
import com.classic.mvvmapplication.di.modules.MovieAdapterModule;
import com.classic.mvvmapplication.ui.Adapters.MovieAdapter;
import com.classic.mvvmapplication.ui.fragments.LoginFragment;
import com.classic.mvvmapplication.ui.fragments.PopularMoviesFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector()
    abstract SplashFragment contributeSplashFragment();


    @ContributesAndroidInjector()
    abstract LoginFragment contributeLoginFragment();

    @ContributesAndroidInjector(modules = MovieAdapterModule.class)
    abstract PopularMoviesFragment contributePopularMoviesFragment();

}
