package com.classic.mvvmapplication.data.local;

import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.models.local.VideoLocal;

import java.util.List;

import io.reactivex.Completable;

public interface VideoDBHelper {

    Completable insertVideo(VideoLocal video);

    Completable insertVideoList(List<VideoLocal> videoLocalList);

    LiveData<List<VideoLocal>> getVideoListByMovieId(int movieId);

    LiveData<List<VideoLocal>> getVideoListByTvId(int tvId);
}
