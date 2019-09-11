package com.classic.mvvmapplication.ui.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.databinding.MovieListItemBinding;
import com.classic.mvvmapplication.ui.bindingModels.MoviePosterItemBindingModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {


    private ArrayList<Movie> dataset;
    private MovieAdapterListener mListener;

    public MovieAdapter(ArrayList<Movie> movies){
        this.dataset=movies;
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
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        MovieListItemBinding movieListItemBinding = MovieListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ViewHolder(movieListItemBinding,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        holder.bindModel(position,dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addItems(List<Movie> movieList) {
        dataset.addAll(movieList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        dataset.clear();
    }

    public void setListener(MovieAdapterListener listener) {
        this.mListener = listener;
    }

    public interface MovieAdapterListener {

        void onMovieClick(int MovieId);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements MoviePosterItemBindingModel.MovieItemListener{

        private MovieListItemBinding movieListItemBinding;
        private MoviePosterItemBindingModel moviePosterItemBindingModel;
        private MovieAdapterListener movieAdapterListener;


        public ViewHolder(MovieListItemBinding movieListItemBinding, MovieAdapterListener mListener){
            super(movieListItemBinding.getRoot());

            this.movieAdapterListener=mListener;
            this.movieListItemBinding = movieListItemBinding;
        }


        void bindModel(int position,Movie movie)
        {
            moviePosterItemBindingModel = new MoviePosterItemBindingModel(movie, this);
            movieListItemBinding.setMoviePosterItemBindingModel(moviePosterItemBindingModel);

            movieListItemBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(int movieId) {
            Timber.d("on Item clicked ");
            movieAdapterListener.onMovieClick(movieId);
        }
    }
}
