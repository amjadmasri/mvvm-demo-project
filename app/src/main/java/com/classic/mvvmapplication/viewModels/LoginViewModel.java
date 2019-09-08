package com.classic.mvvmapplication.viewModels;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.classic.mvvmapplication.data.models.api.CreateGuestSessionResponse;
import com.classic.mvvmapplication.data.models.api.CreateUserSessionResponse;
import com.classic.mvvmapplication.data.models.api.LoginResponse;
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
    private MutableLiveData<Resource<Boolean>> status= new MutableLiveData<>();
    private String requestToken;

    private UserRepository userRepository;

    @Inject
    public LoginViewModel(UserRepository dataRepository) {
        super(dataRepository);

        userRepository=(UserRepository) getDataRepository();

        requestNewToken();
    }

    private void requestNewToken() {
       // status.setValue(Resource.<Boolean>loading(null));
        userRepository.getRequestToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<LoginResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<LoginResponse> loginResponseResponse) {
                        if(loginResponseResponse.isSuccessful()){
                            LoginResponse loginResponse = loginResponseResponse.body();
                            if(loginResponse.getSuccess()) {
                                requestToken=loginResponse.getRequestToken();

                               // status.setValue(Resource.<Boolean>success(null));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }


    public LiveData<String> getEmailError() {
        return emailError;
    }

    public LiveData<String> getPasswordError() {
        return passwordError;
    }

    public LiveData<Resource<Boolean>> getStatusLiveData(){
        return status;
    }

    public void validateAndLogin(String email, String password) {
        if(email.isEmpty()){
            emailError.setValue("please enter an email ");
            return;
        }
        else if(!isEmailValid(email)){
            emailError.setValue("email Not Valid");
            return;
        }
        else if (password.isEmpty()){
            passwordError.setValue("please enter a password ");
            return;
        }

        else{
            serverLogin(email,password);
        }
    }

    public void loginAsGuest(){
        status.setValue(Resource.<Boolean>loading(null));
        if(requestToken!=null&&!requestToken.isEmpty()){
            userRepository.createGuestSession()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<Response<CreateGuestSessionResponse>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(Response<CreateGuestSessionResponse> createGuestSessionResponseResponse) {
                            if(createGuestSessionResponseResponse.isSuccessful()){
                                CreateGuestSessionResponse createGuestSessionResponse = createGuestSessionResponseResponse.body();
                                if(createGuestSessionResponse.getSuccess()) {
                                    userRepository.saveSessionToken(createGuestSessionResponse.getGuestSessionId());

                                    status.setValue(Resource.<Boolean>success(null));
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            status.setValue(Resource.<Boolean>error(e.getLocalizedMessage(),null));
                        }
                    });
        }
        else{
            Timber.d("hrere? "+requestToken);
        }
    }

    private void serverLogin(String email, String password) {
        status.setValue(Resource.<Boolean>loading(null));
        if(requestToken!=null &&!requestToken.isEmpty()){
            userRepository.serverLoginUser(email,password,requestToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<Response<LoginResponse>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(Response<LoginResponse> loginResponseResponse) {
                            if(loginResponseResponse.isSuccessful()){
                                LoginResponse loginResponse = loginResponseResponse.body();
                                if(loginResponse.getSuccess()) {
                                    createNewUserSession(loginResponse.getRequestToken());
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            status.setValue(Resource.<Boolean>error(e.getLocalizedMessage(),null));
                        }
                    });
        }
    }

    private void createNewUserSession(String requestToken) {
        userRepository.createUserSession(requestToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CreateUserSessionResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<CreateUserSessionResponse> createUserSessionResponseResponse) {
                        if(createUserSessionResponseResponse.isSuccessful()){
                            CreateUserSessionResponse createUserSessionResponse = createUserSessionResponseResponse.body();
                            if(createUserSessionResponse.getSuccess()) {
                                userRepository.saveSessionToken(createUserSessionResponse.getSessionId());

                                status.setValue(Resource.<Boolean>success(null));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        status.setValue(Resource.<Boolean>error(e.getLocalizedMessage(),null));
                    }
                });
    }

    private boolean isEmailValid(String email) {
        return true;
    }
}
