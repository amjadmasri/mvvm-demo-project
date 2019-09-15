package com.classic.mvvmapplication.ui.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.classic.mvvmapplication.data.models.local.VideoLocal;
import com.classic.mvvmapplication.databinding.VideoListItemBinding;
import com.classic.mvvmapplication.ui.bindingModels.VideoItemBindingModel;
import com.classic.mvvmapplication.utilities.AppConstants;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private final List<VideoLocal> dataset;
    private VideoItemClick videoItemClick;

    public VideoAdapter(List<VideoLocal> videoLocalList) {
        this.dataset = videoLocalList;

    }

    public void setVideoItemClick(VideoItemClick videoItemClick){
        this.videoItemClick= videoItemClick;
    }

    public void setData(List<VideoLocal> newData) {
        if (newData != null) {
            dataset.clear();
            dataset.addAll(newData);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideoListItemBinding videoListItemBinding = VideoListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ViewHolder(videoListItemBinding,videoItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindModel(position,dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public interface VideoItemClick{
        void onVideoClicked(String youtubeURL);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements VideoItemBindingModel.VideoItemListener {

        private final VideoItemClick videoItemClick;
        private VideoListItemBinding videoListItemBinding;


        private VideoItemBindingModel videoItemBindingModel;


        public ViewHolder(VideoListItemBinding videoListItemBinding,VideoItemClick videoItemClick) {
            super(videoListItemBinding.getRoot());

            this.videoListItemBinding = videoListItemBinding;
            this.videoItemClick=videoItemClick;
        }


        void bindModel(int position, VideoLocal videoLocal) {
            videoItemBindingModel = new VideoItemBindingModel(videoLocal, this);
            videoListItemBinding.setVideoItemBinding(videoItemBindingModel);

            videoListItemBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(String videoYouTubeId) {
            videoItemClick.onVideoClicked(videoYouTubeId);
        }
    }


}
