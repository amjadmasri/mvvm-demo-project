package com.classic.mvvmapplication.di.modules;

import androidx.lifecycle.ViewModel;

import com.classic.mvvmapplication.di.interfaces.ViewModelKey;
import com.classic.mvvmapplication.viewModels.MovieViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel.class)
    public abstract ViewModel bindMovieViewModel(MovieViewModel movieViewModel);
}
