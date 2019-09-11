package com.classic.mvvmapplication.data.local;

import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.AppDatabase;
import com.classic.mvvmapplication.data.models.local.VideoLocal;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;

public class AppVideoDBHelper implements VideoDBHelper {

    private final AppDatabase appDataBase;

    @Inject
    public AppVideoDBHelper(AppDatabase appDatabase){
        this.appDataBase =appDatabase;
    }

    @Override
    public Completable insertVideo(VideoLocal video) {
        return appDataBase.getVideoDao().insert(video);
    }

    @Override
    public Completable insertVideoList(List<VideoLocal> videoLocalList) {
        return appDataBase.getVideoDao().insertList(videoLocalList);
    }

    @Override
    public LiveData<List<VideoLocal>> getVideoListByMovieId(int movieId) {
        return appDataBase.getVideoDao().getVideosByMovieId(movieId);
    }

    @Override
    public LiveData<List<VideoLocal>> getVideoListByTvId(int tvId) {
        return appDataBase.getVideoDao().getVideosByTvId(tvId);
    }
}
