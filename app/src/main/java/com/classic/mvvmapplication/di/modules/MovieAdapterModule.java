package com.classic.mvvmapplication.di.modules;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.ui.Adapters.MovieAdapter;
import com.classic.mvvmapplication.ui.main.MainActivity;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieAdapterModule {

    @Provides
    MovieAdapter provideMovieAdapter() {
        return new MovieAdapter(new ArrayList<Movie>());
    }

    @Provides
    GridLayoutManager provideGridLayoutManager(MainActivity mainActivity) {
        return new GridLayoutManager(mainActivity,2);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(MainActivity mainActivity) {
        return new LinearLayoutManager(mainActivity);
    }
}