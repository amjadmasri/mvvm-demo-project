package com.classic.mvvmapplication.ui.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.classic.mvvmapplication.data.models.local.ReviewLocal;
import com.classic.mvvmapplication.databinding.ReviewListItemBinding;
import com.classic.mvvmapplication.ui.bindingModels.ReviewItemBindingModel;


import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ReviewPagedAdapter extends PagedListAdapter<ReviewLocal, ReviewPagedAdapter.ViewHolder> {


    private ArrayList<ReviewLocal> dataset;
    private ReviewLocalAdapterListener mListener;

    public ReviewPagedAdapter(@NonNull DiffUtil.ItemCallback<ReviewLocal> diffCallback) {
        super(diffCallback);
    }


    public void setData(List<ReviewLocal> newData) {
        if(newData!=null) {
            dataset.clear();
            dataset.addAll(newData);
            notifyDataSetChanged();
        }
    }



    @NonNull
    @Override
    public ReviewPagedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReviewListItemBinding reviewListItemBinding = ReviewListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ReviewPagedAdapter.ViewHolder(reviewListItemBinding,mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ReviewPagedAdapter.ViewHolder holder, int position) {
        if(getItem(position)!=null) {
            holder.bindModel(position, getItem(position));
        }

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public void addItems(List<ReviewLocal> reviewLocalList) {
        dataset.addAll(reviewLocalList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        dataset.clear();
    }

    public void setListener(ReviewLocalAdapterListener listener) {
        this.mListener = listener;
    }

    public interface ReviewLocalAdapterListener {

        void onReviewLocalClick(String reviewId);
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements ReviewItemBindingModel.ReviewItemListener {
        private ReviewListItemBinding reviewListItemBinding;
        private ReviewItemBindingModel reviewItemBindingModel;
        private ReviewLocalAdapterListener reviewLocalAdapterListener;




        public ViewHolder(ReviewListItemBinding reviewLocalListItemBinding, ReviewLocalAdapterListener reviewLocalAdapterListener){
            super(reviewLocalListItemBinding.getRoot());

            this.reviewLocalAdapterListener=reviewLocalAdapterListener;
            this.reviewListItemBinding = reviewLocalListItemBinding;
        }


        void bindModel(int position, ReviewLocal reviewLocal)
        {
            reviewItemBindingModel = new ReviewItemBindingModel(reviewLocal, this);
            reviewListItemBinding.setReviewItem(reviewItemBindingModel);

            reviewListItemBinding.executePendingBindings();
        }


        @Override
        public void onItemClick(String reviewId) {
            reviewLocalAdapterListener.onReviewLocalClick(reviewId);
        }
    }
}
