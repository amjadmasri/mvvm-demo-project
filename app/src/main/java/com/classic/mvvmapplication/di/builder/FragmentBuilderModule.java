package com.classic.mvvmapplication.di.builder;

import com.classic.mvvmapplication.SplashFragment;
import com.classic.mvvmapplication.di.modules.MovieAdapterModule;
import com.classic.mvvmapplication.di.modules.ReviewAdapterModule;
import com.classic.mvvmapplication.di.modules.VideoAdapterModule;
import com.classic.mvvmapplication.ui.Adapters.MovieAdapter;
import com.classic.mvvmapplication.ui.Adapters.VideoAdapter;
import com.classic.mvvmapplication.ui.fragments.LoginFragment;
import com.classic.mvvmapplication.ui.fragments.MovieDetailsFragment;
import com.classic.mvvmapplication.ui.fragments.PopularMoviesFragment;
import com.classic.mvvmapplication.ui.fragments.ReviewDetailsFragment;
import com.classic.mvvmapplication.ui.fragments.SettingsFragment;

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

    @ContributesAndroidInjector
    abstract SettingsFragment contributeSettingsFragment();

    @ContributesAndroidInjector(modules = {VideoAdapterModule.class, ReviewAdapterModule.class,MovieAdapterModule.class})
    abstract MovieDetailsFragment contributeMovieDetailsFragment();

    @ContributesAndroidInjector
    abstract ReviewDetailsFragment contributeReviewDetailsFragment();

}
