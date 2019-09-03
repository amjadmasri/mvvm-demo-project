package com.classic.mvvmapplication.utilities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.classic.mvvmapplication.data.models.local.Movie;

public class MovieDiffCallBacks extends DiffUtil.ItemCallback<Movie> {
    @Override
    public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
        return false;
    }
}
