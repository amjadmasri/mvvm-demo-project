package com.classic.mvvmapplication.ui.bindingModels;

import androidx.databinding.ObservableField;

import com.classic.mvvmapplication.data.models.local.ReviewLocal;

public class ReviewItemBindingModel {

    public final ObservableField<String> title;

    public final ObservableField<String> content;

    public final ReviewItemListener reviewItemListener;

    private final ReviewLocal reviewLocal;

    public ReviewItemBindingModel( ReviewLocal reviewLocal,ReviewItemListener reviewItemListener) {
        this.reviewItemListener = reviewItemListener;
        this.reviewLocal = reviewLocal;

        this.title= new ObservableField<>(reviewLocal.getAuthor());
        this.content=new ObservableField<>(reviewLocal.getContent());

    }

    public void onItemClick() {
        reviewItemListener.onItemClick(reviewLocal.getId());
    }


    public interface ReviewItemListener {

        void onItemClick(String reviewId);
    }
}
