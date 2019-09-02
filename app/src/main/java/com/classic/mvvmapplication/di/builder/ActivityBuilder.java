package com.classic.mvvmapplication.di.builder;

import com.classic.mvvmapplication.di.modules.MovieAdapterModule;
import com.classic.mvvmapplication.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = MovieAdapterModule.class)
    abstract MainActivity bindMainActivity();

}
