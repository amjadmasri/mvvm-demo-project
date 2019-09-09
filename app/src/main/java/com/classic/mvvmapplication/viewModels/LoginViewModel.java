package com.classic.mvvmapplication.viewModels;

import android.util.Patterns;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.classic.mvvmapplication.data.models.api.CreateGuestSessionResponse;
import com.classic.mvvmapplication.data.models.api.CreateUserSessionResponse;
import com.classic.mvvmapplication.data.models.api.LoginResponse;
import com.classic.mvvmapplication.data.models.local.User;
import com.classic.mvvmapplication.data.repositories.interfaces.DataRepository;
import com.classic.mvvmapplication.data.repositories.interfaces.UserRepository;
import com.classic.mvvmapplication.utilities.Resource;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import timber.log.Timber;

public class LoginViewModel extends BaseViewModel {

    private MutableLiveData<String> emailError = new MutableLiveData<>();
    private MutableLiveData<String> passwordError = new MutableLiveData<>();
    private MutableLiveData<Resource<String>> loginStatus= new MutableLiveData<>();
    private LiveData<Resource<String>> getNewTokenLiveData= new MutableLiveData<>();
    private String requestToken;

    private UserRepository userRepository;

    @Inject
    public LoginViewModel(UserRepository dataRepository) {
        super(dataRepository);

        userRepository=(UserRepository) getDataRepository();

        requestNewToken();
    }

    private void requestNewToken() {
        getNewTokenLiveData=Transformations.map(userRepository.getRequestToken(), new Function<Resource<String>, Resource<String>>() {
            @Override
            public Resource<String> apply(Resource<String> input) {
                if(input.status.equals(Resource.Status.SUCCESS)){
                    requestToken=input.data;
                }
                return input;
            }
        });
    }


    public LiveData<String> getEmailError() {
        return emailError;
    }

    public LiveData<String> getPasswordError() {
        return passwordError;
    }

    public boolean validateLogin(String email, String password) {
        if(email.isEmpty()){
            emailError.setValue("please enter an email ");
            return false;
        }
        else if(!isEmailValid(email)){
            emailError.setValue("email Not Valid");
            return false;
        }
        else if (password.isEmpty()){
            passwordError.setValue("please enter a password ");
            return false;
        }

        else{
            return true;
        }
    }

    public LiveData<Resource<String>> loginUser(String email, String password){
        return userRepository.serverLoginUser(email,password,requestToken);
    }

    public LiveData<Resource<String>> loginAsGuest(){
        return userRepository.createGuestSession();
    }

    private boolean isEmailValid(String email) {
        return true;
    }

    public LiveData<Resource<String>> getGetNewTokenLiveData() {
        return getNewTokenLiveData;
    }
}
