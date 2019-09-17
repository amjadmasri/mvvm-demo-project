package com.classic.mvvmapplication.di.modules;

import androidx.lifecycle.ViewModel;

import com.classic.mvvmapplication.di.interfaces.ViewModelKey;
import com.classic.mvvmapplication.viewModels.LoginViewModel;
import com.classic.mvvmapplication.viewModels.MovieDetailsViewModel;
import com.classic.mvvmapplication.viewModels.MovieViewModel;
import com.classic.mvvmapplication.viewModels.ReviewDetailsViewModel;
import com.classic.mvvmapplication.viewModels.ReviewListViewModel;
import com.classic.mvvmapplication.viewModels.UserViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel.class)
    abstract ViewModel bindMovieViewModel(MovieViewModel movieViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel.class)
    abstract ViewModel bindMovieDetailsViewModel(MovieDetailsViewModel movieDetailsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ReviewDetailsViewModel.class)
    abstract ViewModel bindReviewDetailsViewModel(ReviewDetailsViewModel reviewDetailsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ReviewListViewModel.class)
    abstract ViewModel bindReviewListViewModel(ReviewListViewModel reviewListViewModel);
}
