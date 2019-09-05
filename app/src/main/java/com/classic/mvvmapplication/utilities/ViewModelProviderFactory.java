package com.classic.mvvmapplication.utilities;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;


    @Inject
    public ViewModelProviderFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        this.creators = creators;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        Timber.d(modelClass.getName());
        Timber.d(""+creators.size());

        for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : creators.entrySet()) {
            if (modelClass.isAssignableFrom(entry.getKey())) {

                Timber.d(entry.getKey().getName());
                Timber.d(entry.getValue().toString());
            }
        }

        Provider<? extends ViewModel> creator = creators.get(modelClass);
        if (creator == null) {
            Timber.d("inside null");
            for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : creators.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException("unknown model class " + modelClass);
        }

        try {
          //  Timber.d(modelClass.getName());
            //Timber.d(creator.get().toString());

            return (T) creator.get();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

    }
}
