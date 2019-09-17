package com.classic.mvvmapplication.utilities.diffCallbacks;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.classic.mvvmapplication.data.models.local.ReviewLocal;

public class ReviewDiffCallBacks extends DiffUtil.ItemCallback<ReviewLocal> {
    @Override
    public boolean areItemsTheSame(@NonNull ReviewLocal oldItem, @NonNull ReviewLocal newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull ReviewLocal oldItem, @NonNull ReviewLocal newItem) {
        return false;
    }
}
