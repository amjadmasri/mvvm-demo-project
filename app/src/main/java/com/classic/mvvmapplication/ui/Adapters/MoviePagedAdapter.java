package com.classic.mvvmapplication.ui.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.databinding.MovieListItemBinding;
import com.classic.mvvmapplication.ui.bindingModels.MovieBindingModel;
import com.classic.mvvmapplication.utilities.AppConstants;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MoviePagedAdapter  extends PagedListAdapter<Movie,MoviePagedAdapter.ViewHolder> {


    private ArrayList<Movie> dataset;
    private MovieAdapter.MovieAdapterListener mListener;

    public MoviePagedAdapter(@NonNull DiffUtil.ItemCallback<Movie> diffCallback) {
        super(diffCallback);
    }


    public void setData(List<Movie> newData) {
        if(newData!=null) {
            dataset.clear();
            dataset.addAll(newData);
            notifyDataSetChanged();
        }
    }



    @NonNull
    @Override
    public MoviePagedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieListItemBinding movieListItemBinding = MovieListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new MoviePagedAdapter.ViewHolder(movieListItemBinding,mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull MoviePagedAdapter.ViewHolder holder, int position) {
        if(getItem(position)!=null) {
            holder.bindModel(position, getItem(position));
        }

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public void addItems(List<Movie> movieList) {
        dataset.addAll(movieList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        dataset.clear();
    }

    public void setListener(MovieAdapter.MovieAdapterListener listener) {
        this.mListener = listener;
    }

    public interface MovieAdapterListener {

        void onMovieClick();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements MovieBindingModel.MovieItemListener{
        private MovieListItemBinding movieListItemBinding;
        private MovieBindingModel movieBindingModel;
        private MovieAdapter.MovieAdapterListener movieAdapterListener;




        public ViewHolder(MovieListItemBinding movieListItemBinding, MovieAdapter.MovieAdapterListener movieAdapterListener){
            super(movieListItemBinding.getRoot());

            this.movieAdapterListener=movieAdapterListener;
            this.movieListItemBinding = movieListItemBinding;
        }


        void bindModel(int position, Movie movie)
        {
            movieBindingModel = new MovieBindingModel(movie, this);
            movieListItemBinding.setMovieBindingModel(movieBindingModel);

            movieListItemBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(int movieId) {
            Timber.d("on Item clicked ");
            movieAdapterListener.onMovieClick();
        }
    }
}
