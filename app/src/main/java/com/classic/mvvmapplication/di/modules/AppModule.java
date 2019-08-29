package com.classic.mvvmapplication.di.modules;


import android.content.Context;

import androidx.room.Room;

import com.classic.mvvmapplication.data.ApiService;
import com.classic.mvvmapplication.data.AppDatabase;
import com.classic.mvvmapplication.di.interfaces.ApiKeyInfo;
import com.classic.mvvmapplication.di.interfaces.ApiURlInfo;
import com.classic.mvvmapplication.di.interfaces.DatabaseInfo;
import com.classic.mvvmapplication.di.interfaces.DateFormatInfo;
import com.classic.mvvmapplication.utilities.AppConstants;
import com.classic.mvvmapplication.utilities.BooleanDeserializer;
import com.classic.mvvmapplication.utilities.DateDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @ApiKeyInfo
    String provideAPIKey() {
        return AppConstants.API_KEY;
    }

    @Provides
    @DateFormatInfo
    String provideDateFormat(){
        return AppConstants.DATE_FORMAT;
    }

    @Provides
    @ApiURlInfo
    String provideApiBaseURL(){
        return AppConstants.API_BASE_URL;
    }



    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient,
                             GsonConverterFactory gsonConverterFactory, @ApiURlInfo String baseUrl){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    public ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }


    @Provides
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor){

        return new OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }


    @Provides
    public HttpLoggingInterceptor provideHTTPLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Singleton
    @Provides
    public Gson provideGson(@DateFormatInfo String dateFormat, BooleanDeserializer booleanDeserializer ){

        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder
                .setLenient()
                .setDateFormat(dateFormat)
                .registerTypeAdapter(Boolean.class, booleanDeserializer)
                .registerTypeAdapter(boolean.class, booleanDeserializer)
                .create();
    }

    @Singleton
    @Provides
    public BooleanDeserializer provideBooleanDeserializer(){
        BooleanDeserializer booleanDeserializer = new BooleanDeserializer();
        return booleanDeserializer;
    }
    @Singleton
    @Provides
    public DateDeserializer provideDateDeserializer(){
        DateDeserializer dateDeserializer = new DateDeserializer();
        return dateDeserializer;
    }

}
