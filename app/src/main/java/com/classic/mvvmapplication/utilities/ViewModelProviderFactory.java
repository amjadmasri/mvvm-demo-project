package com.classic.mvvmapplication.utilities;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.classic.mvvmapplication.data.DataManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    private final DataManager dataManager;

    @Inject
    public ViewModelProviderFactory(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        /*
        if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            return (T) new MovieViewModel(dataManager);
        } else if (modelClass.isAssignableFrom(FeedViewModel.class)) {
            return (T) new FeedViewModel(dataManager,schedulerProvider);
        }
        */
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
