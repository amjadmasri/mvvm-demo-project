package com.classic.mvvmapplication.data.repositories.implementations;

import android.content.Context;
import android.os.AsyncTask;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.classic.mvvmapplication.data.api.UserApiHelper;
import com.classic.mvvmapplication.data.local.UserDbHelper;
import com.classic.mvvmapplication.data.models.api.CreateGuestSessionResponse;
import com.classic.mvvmapplication.data.models.api.CreateUserSessionResponse;
import com.classic.mvvmapplication.data.models.api.LoginResponse;
import com.classic.mvvmapplication.data.models.local.User;
import com.classic.mvvmapplication.data.prefs.PreferencesHelper;
import com.classic.mvvmapplication.data.repositories.interfaces.UserRepository;
import com.classic.mvvmapplication.utilities.Resource;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import timber.log.Timber;

public class AppUserRepository implements UserRepository {

    private final Context mContext;
    private final UserDbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final UserApiHelper mApiHelper;

    @Inject
    public AppUserRepository(Context context, UserDbHelper dbHelper, PreferencesHelper preferencesHelper, UserApiHelper apiHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public LiveData<User> getUser() {
        return mDbHelper.getUser();
    }

    @Override
    public void saveUser(User user) {
        new AsyncTask<User, Void, Void>() {
            @Override
            protected Void doInBackground(User... users) {
                mDbHelper.saveUser(users[0]);
                return null;
            }
        }.execute(user);
    }

    @Override
    public LiveData<Resource<String>> serverLoginUser(String email, String password,String requestToken) {
        final MediatorLiveData<Resource<String>> midiatedResults= new MediatorLiveData<>();
        final MutableLiveData<Resource<String>> result = new MutableLiveData<>();

        midiatedResults.addSource(result, new Observer<Resource<String>>() {
            @Override
            public void onChanged(Resource<String> stringResource) {
                midiatedResults.setValue(stringResource);
            }
        });

        result.setValue(Resource.<String>loading("Attempting Login"));

         mApiHelper.serverLoginUser(email,password,requestToken)
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
                                 result.setValue(Resource.<String>success(null));

                                 midiatedResults.addSource(createUserSession(loginResponse.getRequestToken()), new Observer<Resource<String>>() {
                                     @Override
                                     public void onChanged(Resource<String> stringResource) {
                                         midiatedResults.setValue(stringResource);
                                     }
                                 });
                             }
                             else{
                                 result.setValue(Resource.<String>error("there was an unknown error",null));
                             }
                         }
                         else{
                             result.setValue(Resource.<String>error("there was an unknown error",null));
                         }
                     }

                     @Override
                     public void onError(Throwable e) {
                         result.setValue(Resource.<String>error(e.getLocalizedMessage(),"failed"));
                     }
                 });

         return result;
    }

    @Override
    public LiveData<Resource<String>> getRequestToken() {
        final MutableLiveData<Resource<String>> result = new MutableLiveData<>();
        result.setValue(Resource.<String>loading("please wait for a moment"));
         mApiHelper.getRequestToken()
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
                                 result.setValue(Resource.<String>success(loginResponse.getRequestToken()));
                             }
                             else{
                                 result.setValue(Resource.<String>error("please try again later","failed"));
                             }
                         }
                     }

                     @Override
                     public void onError(Throwable e) {
                         result.setValue(Resource.<String>error(e.getMessage(),"failed"));
                     }
                 });

         return result;
    }

    @Override
    public LiveData<Resource<String>> createUserSession(String requestToken) {
        final MutableLiveData<Resource<String>> result = new MutableLiveData<>();
        result.setValue(Resource.<String>loading("Creating User Session"));
        mApiHelper.createUserSession(requestToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(new SingleObserver<Response<CreateUserSessionResponse>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(Response<CreateUserSessionResponse> createUserSessionResponseResponse) {
                    if(createUserSessionResponseResponse.isSuccessful()){
                        CreateUserSessionResponse createUserSessionResponse = createUserSessionResponseResponse.body();
                        if(createUserSessionResponse.getSuccess()) {
                            saveSessionToken(createUserSessionResponse.getSessionId());
                            saveLocalUser(createUserSessionResponse.getSessionId(),null,false);
                            result.setValue(Resource.<String>success("created Session Successfully"));
                        }
                    }
                }

                @Override
                public void onError(Throwable e) {
                    result.setValue(Resource.<String>error(e.getMessage(),"failed"));
                }
            });
        return result;
    }

    @Override
    public LiveData<Resource<String>> createGuestSession() {
        final MutableLiveData<Resource<String>> result = new MutableLiveData<>();
        result.setValue(Resource.<String>loading("Creating Guest Session"));
        mApiHelper.createGuestSession()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<CreateGuestSessionResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<CreateGuestSessionResponse> createGuestSessionResponseResponse) {
                        if(createGuestSessionResponseResponse.isSuccessful()){
                            CreateGuestSessionResponse createGuestSessionResponse = createGuestSessionResponseResponse.body();
                            if(createGuestSessionResponse.getSuccess()) {
                                saveSessionToken(createGuestSessionResponse.getGuestSessionId());
                                saveLocalUser(createGuestSessionResponse.getGuestSessionId(),createGuestSessionResponse.getExpiresAt(),true);
                                result.setValue(Resource.<String>success("created Guest Session Successfully"));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.setValue(Resource.<String>error(e.getMessage(),"failed"));
                    }
                });
        return result;
    }

    @Override
    public void saveSessionToken(String userSession){
        mPreferencesHelper.setSessionKey(userSession);
    }

    private void saveLocalUser(String guestSessionId, String expiresAt,boolean isGuest) {
        User user = new User();
        user.setSessionKey(guestSessionId);
        user.setSessionKeyExpireDate(expiresAt);
        if(isGuest)
            user.setUsername("Guest");
        saveUser(user);
    }

    @Override
    public LiveData<Resource<String>> logOutUser() {
       final MediatorLiveData<Resource<String>> result = new MediatorLiveData<>();

       final LiveData<Resource<String>> stringLiveData =Transformations.map(getUser(), new Function<User, Resource<String>>() {
           @Override
           public Resource<String> apply(User input) {
               if(input==null)
                   return Resource.error("user Not Found ","error");
               else
                   return Resource.success(input.getId().toString());
           }
       });

       result.addSource(stringLiveData, new Observer<Resource<String>>() {
           @Override
           public void onChanged(Resource<String> stringResource) {
               switch (stringResource.status){
                   case ERROR:
                       Timber.d(stringResource.message+" "+stringResource.data);
                       result.setValue(Resource.success("logged out"));
                       break;
                   case SUCCESS:
                       result.removeSource(stringLiveData);
                       result.addSource(removeUser(Integer.parseInt(stringResource.data)), new Observer<Boolean>() {
                           @Override
                           public void onChanged(Boolean aBoolean) {
                               if(aBoolean){
                                   result.setValue(Resource.success("success"));
                               }
                               else{
                                   result.setValue(Resource.error("failed to delete user","failed"));
                               }
                           }
                       });
                       break;
               }
           }
       });
        return result;
    }

    private LiveData<Boolean> removeUser(int userId) {
        final MutableLiveData<Boolean> result=new MutableLiveData<>();
        mDbHelper.deleteUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        result.setValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.setValue(false);
                    }
                });
        return result;
    }
}
