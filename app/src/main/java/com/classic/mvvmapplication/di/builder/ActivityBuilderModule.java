package com.classic.mvvmapplication.di.builder;

import com.classic.mvvmapplication.di.modules.MovieAdapterModule;
import com.classic.mvvmapplication.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract MainActivity bindMainActivity();

}
