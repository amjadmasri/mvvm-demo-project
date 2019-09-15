package com.classic.mvvmapplication.ui.bindingModels;

import androidx.databinding.ObservableField;

import com.classic.mvvmapplication.data.models.local.VideoLocal;

public class VideoItemBindingModel {

    public final ObservableField<String> videoThumbnail;

    public final ObservableField<String> videoTitle;

    public final VideoItemListener videoItemListener;

    private final VideoLocal videoLocal;

    public VideoItemBindingModel(VideoLocal videoLocal, VideoItemListener videoItemListener) {
        this.videoThumbnail = new ObservableField<>(videoLocal.getKey());
        this.videoTitle = new ObservableField<>(videoLocal.getName());
        this.videoItemListener = videoItemListener;
        this.videoLocal = videoLocal;
    }

    public void onItemClick() {
        videoItemListener.onItemClick(videoLocal.getId());
    }


    public interface VideoItemListener {

        void onItemClick(String videoYoutubeId);
    }
}
