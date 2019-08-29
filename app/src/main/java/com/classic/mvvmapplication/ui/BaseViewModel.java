package com.classic.mvvmapplication.ui;

import androidx.lifecycle.ViewModel;

import com.classic.mvvmapplication.data.DataManager;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel <N> extends ViewModel {

    private final DataManager dataManager;

    public BaseViewModel(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    public DataManager getDataManager() {
        return dataManager;
    }
}
