package com.classic.mvvmapplication.utilities;

import com.classic.mvvmapplication.data.prefs.PreferencesHelper;
import com.classic.mvvmapplication.di.interfaces.ApiKeyInfo;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class QueryParametersInterceptor implements Interceptor {

    private final String apiKey;
    private final PreferencesHelper preferenceHelper;

    @Inject
    public QueryParametersInterceptor(@ApiKeyInfo String apiKey, PreferencesHelper preferencesHelper){
        this.apiKey=apiKey;
        this.preferenceHelper=preferencesHelper;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", apiKey)
                .addQueryParameter("language",preferenceHelper.getDataLanguage())
                .build();

        Request.Builder requestBuilder = original.newBuilder()
                .addHeader("Content-Type", "application/json")
                .url(url);


        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

}
