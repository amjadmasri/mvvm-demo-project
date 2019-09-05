package com.classic.mvvmapplication.di.components;


import android.app.Application;

import com.classic.mvvmapplication.MvvmApp;
import com.classic.mvvmapplication.di.builder.ActivityBuilderModule;
import com.classic.mvvmapplication.di.modules.AppModule;
import com.classic.mvvmapplication.di.modules.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilderModule.class, ViewModelModule.class})
public interface AppComponent {

    void inject(MvvmApp app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }


}
