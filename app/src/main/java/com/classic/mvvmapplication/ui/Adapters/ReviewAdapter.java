package com.classic.mvvmapplication.ui.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.classic.mvvmapplication.data.models.local.ReviewLocal;
import com.classic.mvvmapplication.databinding.ReviewListItemBinding;
import com.classic.mvvmapplication.ui.bindingModels.ReviewItemBindingModel;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private final List<ReviewLocal> dataset;
    private ReviewItemClick reviewItemClick;

    public ReviewAdapter(List<ReviewLocal> reviewLocalList) {
        this.dataset = reviewLocalList;

    }

    public void setReviewItemClick(ReviewItemClick reviewItemClick){
        this.reviewItemClick= reviewItemClick;
    }

    public void setData(List<ReviewLocal> newData) {
        if (newData != null) {
            dataset.clear();
            dataset.addAll(newData);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReviewListItemBinding reviewListItemBinding = ReviewListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ViewHolder(reviewListItemBinding,reviewItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindModel(position,dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public interface ReviewItemClick{
        void onReviewClicked(String reviewId);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements ReviewItemBindingModel.ReviewItemListener {

        private final ReviewItemClick reviewItemClick;
        private ReviewListItemBinding reviewListItemBinding;


        private ReviewItemBindingModel reviewItemBindingModel;


        public ViewHolder(ReviewListItemBinding reviewListItemBinding,ReviewItemClick reviewItemClick) {
            super(reviewListItemBinding.getRoot());

            this.reviewListItemBinding = reviewListItemBinding;
            this.reviewItemClick=reviewItemClick;
        }


        void bindModel(int position, ReviewLocal reviewLocal) {
            reviewItemBindingModel = new ReviewItemBindingModel(reviewLocal, this);
            reviewListItemBinding.setReviewItem(reviewItemBindingModel);

            reviewListItemBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(String reviewYouTubeId) {
            reviewItemClick.onReviewClicked(reviewYouTubeId);
        }
    }


}
