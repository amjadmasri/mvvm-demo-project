package com.classic.mvvmapplication.ui;

import androidx.lifecycle.ViewModel;

import com.classic.mvvmapplication.data.DataRepository;

public class BaseViewModel  extends ViewModel {

    private final DataRepository dataRepository;

    public BaseViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }


    public DataRepository getDataRepository() {
        return dataRepository;
    }
}
