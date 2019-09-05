package com.classic.mvvmapplication.viewModels;

import androidx.lifecycle.ViewModel;

import com.classic.mvvmapplication.data.repositories.interfaces.DataRepository;

import javax.inject.Inject;

public class LoginViewModel extends BaseViewModel {


    @Inject
    public LoginViewModel(DataRepository dataRepository) {
        super(dataRepository);
    }
}
