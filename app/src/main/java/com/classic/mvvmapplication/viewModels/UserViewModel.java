package com.classic.mvvmapplication.viewModels;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.classic.mvvmapplication.data.models.local.User;
import com.classic.mvvmapplication.data.repositories.interfaces.DataRepository;
import com.classic.mvvmapplication.data.repositories.interfaces.UserRepository;
import com.classic.mvvmapplication.utilities.Resource;

import javax.inject.Inject;

public class UserViewModel extends BaseViewModel {

    private LiveData<User> userLiveData;
    private UserRepository userRepository;

    @Inject
    public UserViewModel(UserRepository dataRepository) {
        super(dataRepository);

        userRepository=(UserRepository) getDataRepository();
    }

    public LiveData<Boolean> checkIfUserExist(){
        return Transformations.switchMap(userRepository.getUser(), new Function<User, LiveData<Boolean>>() {
            @Override
            public LiveData<Boolean> apply(User input) {
                MutableLiveData<Boolean> booleanMutableLiveData = new MutableLiveData<>();
                if(input==null){
                    booleanMutableLiveData.setValue(false);
                    return booleanMutableLiveData;
                }
                else{
                    booleanMutableLiveData.setValue(true);
                    return booleanMutableLiveData;
                }
            }
        });
    }

    public LiveData<Resource<String>> logoutUser(){
        return userRepository.logOutUser();

    }

}
