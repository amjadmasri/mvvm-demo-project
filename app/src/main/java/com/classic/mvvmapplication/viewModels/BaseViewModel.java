package com.classic.mvvmapplication.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.classic.mvvmapplication.data.DataRepository;

public class BaseViewModel  extends ViewModel {

    private final DataRepository dataRepository;

    private MutableLiveData<Boolean> dataLoading = new MutableLiveData<Boolean>();
    private MutableLiveData<Integer> snackBarText = new MutableLiveData<Integer>();

    public BaseViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }


    public DataRepository getDataRepository() {
        return dataRepository;
    }

    public MutableLiveData<Boolean> getDataLoading() {
        return dataLoading;
    }

    public MutableLiveData<Integer> getSnackBarText() {
        return snackBarText;
    }
}
