package com.classic.mvvmapplication.di.modules;


import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.classic.mvvmapplication.AppDataRepository;
import com.classic.mvvmapplication.data.ApiService;
import com.classic.mvvmapplication.data.AppMovieRepository;
import com.classic.mvvmapplication.data.DataRepository;
import com.classic.mvvmapplication.data.MovieRepository;
import com.classic.mvvmapplication.data.AppDatabase;
import com.classic.mvvmapplication.data.api.AppMovieApiHelper;
import com.classic.mvvmapplication.data.api.MovieApiHelper;
import com.classic.mvvmapplication.data.local.AppMovieDbHelper;
import com.classic.mvvmapplication.data.local.MovieDbHelper;
import com.classic.mvvmapplication.data.prefs.AppPreferencesHelper;
import com.classic.mvvmapplication.data.prefs.PreferencesHelper;
import com.classic.mvvmapplication.di.interfaces.ApiKeyInfo;
import com.classic.mvvmapplication.di.interfaces.ApiURlInfo;
import com.classic.mvvmapplication.di.interfaces.DatabaseInfo;
import com.classic.mvvmapplication.di.interfaces.DateFormatInfo;
import com.classic.mvvmapplication.di.interfaces.PreferenceInfo;
import com.classic.mvvmapplication.di.interfaces.ViewModelKey;
import com.classic.mvvmapplication.utilities.ApiErrorMessagesProvider;
import com.classic.mvvmapplication.utilities.QueryParametersInterceptor;
import com.classic.mvvmapplication.utilities.AppConstants;
import com.classic.mvvmapplication.utilities.BooleanDeserializer;
import com.classic.mvvmapplication.utilities.DateDeserializer;
import com.classic.mvvmapplication.viewModels.MovieViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    public ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }


    @Provides
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor, QueryParametersInterceptor queryParametersInterceptor){

        return new OkHttpClient()
                .newBuilder()
                .addInterceptor(queryParametersInterceptor)
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
        return new BooleanDeserializer();
    }
    @Singleton
    @Provides
    public DateDeserializer provideDateDeserializer(){
        return new DateDeserializer();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferenceHelper(AppPreferencesHelper appPreferencesHelper){
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    DataRepository provideDataManager(AppDataRepository appDataRepository) {
        return appDataRepository;
    }

    @Provides
    @Singleton
    MovieDbHelper provideMovieDbHelper(AppMovieDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    MovieApiHelper provideMovieApiHelper(AppMovieApiHelper appMovieApiHelper) {
        return appMovieApiHelper;
    }

    @Singleton
    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }

    @Provides
    public MovieRepository provideMovieRepository(AppMovieRepository appMovieRepository){
        return appMovieRepository;
    }

    @Provides
    public CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }



}
