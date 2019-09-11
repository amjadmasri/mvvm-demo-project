package com.classic.mvvmapplication.data.repositories.interfaces;

import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.models.local.VideoLocal;
import com.classic.mvvmapplication.utilities.Resource;

import java.util.List;

import io.reactivex.Completable;

public interface VideoRepository extends  DataRepository {

    Completable insertVideo(VideoLocal video);

    Completable insertVideoList(List<VideoLocal> videos);

    LiveData<Resource<List<VideoLocal>>> getVideosByMovieId(int movieId);

    LiveData<Resource<List<VideoLocal>>> getVideosByTvId(int tvId);
}
