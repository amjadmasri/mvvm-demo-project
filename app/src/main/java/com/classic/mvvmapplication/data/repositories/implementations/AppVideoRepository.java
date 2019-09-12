package com.classic.mvvmapplication.data.repositories.implementations;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.classic.mvvmapplication.data.api.VideoApiHelper;
import com.classic.mvvmapplication.data.local.VideoDBHelper;
import com.classic.mvvmapplication.data.models.api.VideoRemote;
import com.classic.mvvmapplication.data.models.api.VideoResponse;
import com.classic.mvvmapplication.data.models.local.VideoLocal;
import com.classic.mvvmapplication.data.prefs.PreferencesHelper;
import com.classic.mvvmapplication.data.repositories.interfaces.VideoRepository;
import com.classic.mvvmapplication.utilities.ApiErrorMessagesProvider;
import com.classic.mvvmapplication.utilities.NetworkBoundResource;
import com.classic.mvvmapplication.utilities.Resource;
import com.classic.mvvmapplication.utilities.VideoModelMapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;

public class AppVideoRepository implements VideoRepository {

    private final VideoDBHelper videoDBHelper;
    private final VideoApiHelper videoApiHelper;
    private final PreferencesHelper preferencesHelper;
    private final ApiErrorMessagesProvider apiErrorMessagesProvider;

    @Inject
    public AppVideoRepository(VideoDBHelper videoDBHelper , VideoApiHelper videoApiHelper, PreferencesHelper preferencesHelper, ApiErrorMessagesProvider apiErrorMessagesProvider){
        this.videoDBHelper=videoDBHelper;
        this.videoApiHelper=videoApiHelper;
        this.preferencesHelper=preferencesHelper;
        this.apiErrorMessagesProvider=apiErrorMessagesProvider;
    }

    @Override
    public Completable insertVideo(VideoLocal video) {
        return videoDBHelper.insertVideo(video);
    }

    @Override
    public Completable insertVideoList(List<VideoLocal> videos) {
        return videoDBHelper.insertVideoList(videos);
    }

    @Override
    public LiveData<Resource<List<VideoLocal>>> getVideosByMovieId(final int movieId) {
                return (new NetworkBoundResource<List<VideoLocal>, VideoResponse>(apiErrorMessagesProvider) {
                    @Override
                    protected Completable saveCallResult(@NonNull VideoResponse item) {
                        ArrayList<VideoLocal> videoLocalList = new ArrayList<VideoLocal>();
                        for (VideoRemote videoRemote:item.getResults()) {
                            videoLocalList.add(VideoModelMapper.mapRemoteVideoToLocal(videoRemote,"movie",item.getId()));
                        }

                        return videoDBHelper.insertVideoList(videoLocalList);
                    }

                    @NonNull
                    @Override
                    protected LiveData<List<VideoLocal>> loadFromDb() {
                        return videoDBHelper.getVideoListByMovieId(movieId);
                    }

                    @NonNull
                    @Override
                    protected Single<Response<VideoResponse>> createCall() {
                        return videoApiHelper.getMovieVideos(movieId);
                    }
                }).getAsLiveData();
    }

    @Override
    public LiveData<Resource<List<VideoLocal>>> getVideosByTvId(final int tvId) {
        return (new NetworkBoundResource<List<VideoLocal>, VideoResponse>(apiErrorMessagesProvider) {
            @Override
            protected Completable saveCallResult(@NonNull VideoResponse item) {
                ArrayList<VideoLocal> videoLocalList = new ArrayList<VideoLocal>();
                for (VideoRemote videoRemote:item.getResults()) {
                    videoLocalList.add(VideoModelMapper.mapRemoteVideoToLocal(videoRemote,"movie",item.getId()));
                }

                return videoDBHelper.insertVideoList(videoLocalList);
            }

            @NonNull
            @Override
            protected LiveData<List<VideoLocal>> loadFromDb() {
                return videoDBHelper.getVideoListByMovieId(tvId);
            }

            @NonNull
            @Override
            protected Single<Response<VideoResponse>> createCall() {
                return videoApiHelper.getMovieVideos(tvId);
            }
        }).getAsLiveData();
    }
}
