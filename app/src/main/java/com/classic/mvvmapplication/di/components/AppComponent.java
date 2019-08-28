package com.classic.mvvmapplication.di.components;


import com.classic.mvvmapplication.MvvmApp;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class})
public interface AppComponent {

    void inject(MvvmApp app);

}
