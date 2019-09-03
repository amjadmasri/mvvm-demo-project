package com.classic.mvvmapplication.utilities;

import android.content.Context;

import com.classic.mvvmapplication.R;
import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.HttpException;

@Singleton
public class ApiErrorMessagesProvider {

    private final Context appContext;

    @Inject
    public ApiErrorMessagesProvider(Context appContext){
        this.appContext=appContext;
    }


    public String getCustomErrorMessage(Throwable error){

        if (error instanceof SocketTimeoutException) {
            return appContext.getString(R.string.requestTimeOutError);
        } else if (error instanceof MalformedJsonException) {
            return  appContext.getString(R.string.responseMalformedJson);
        } else if (error instanceof IOException) {
            return  appContext.getString(R.string.networkError);
        } else if (error instanceof HttpException) {
            return (((HttpException) error).response().message());
        } else {
            return appContext.getString(R.string.unknownError);
        }

    }

}
