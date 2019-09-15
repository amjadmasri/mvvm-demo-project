package com.classic.mvvmapplication.di.modules;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.classic.mvvmapplication.data.models.local.VideoLocal;
import com.classic.mvvmapplication.ui.Adapters.VideoAdapter;
import com.classic.mvvmapplication.ui.main.MainActivity;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class VideoAdapterModule {

    @Provides
    VideoAdapter providesVideoAdapter(){
        return new VideoAdapter(new ArrayList<VideoLocal>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(MainActivity mainActivity) {
        return new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
    }
}
