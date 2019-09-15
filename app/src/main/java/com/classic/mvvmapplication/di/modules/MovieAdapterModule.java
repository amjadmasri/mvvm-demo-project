package com.classic.mvvmapplication.di.modules;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.ui.Adapters.MovieAdapter;
import com.classic.mvvmapplication.ui.Adapters.MoviePagedAdapter;
import com.classic.mvvmapplication.ui.fragments.PopularMoviesFragment;
import com.classic.mvvmapplication.ui.main.MainActivity;
import com.classic.mvvmapplication.utilities.MovieDiffCallBacks;
import com.classic.mvvmapplication.utilities.RecyclerViewItemDecorator;

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
    MoviePagedAdapter provideMoviePagedAdapter(MovieDiffCallBacks movieDiffCallBacks) {
        return new MoviePagedAdapter(movieDiffCallBacks);
    }

    @Provides
    MovieDiffCallBacks provideMovieDiffCallBacks() {
        return new MovieDiffCallBacks();
    }

    @Provides
    GridLayoutManager provideGridLayoutManager(PopularMoviesFragment popularMoviesFragment, Context context) {
        boolean isPhone = context.getResources().getBoolean(R.bool.is_phone);
        if(isPhone)
            return new GridLayoutManager(popularMoviesFragment.getActivity(),2);
        else
            return new GridLayoutManager(popularMoviesFragment.getActivity(),3);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(MainActivity mainActivity) {
        return new LinearLayoutManager(mainActivity);
    }

    @Provides
    RecyclerViewItemDecorator provideRecyclerViewItemDecorator(){
        return new RecyclerViewItemDecorator(5);
    }
}
