package com.classic.mvvmapplication.di.builder;

import com.classic.mvvmapplication.SplashFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector()
    abstract SplashFragment contributeSplashFragmentFragment();
}
