package com.classic.mvvmapplication.ui.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.utilities.AppConstants;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {


    private ArrayList<Movie> dataset;

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
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
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


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView moviePoster;
        public final TextView movieTitle;



        public ViewHolder(View view){
            super(view);

            moviePoster =(ImageView)view.findViewById(R.id.item_poster_post);
            movieTitle=(TextView) view.findViewById(R.id.item_poster_title);
        }


        void bindModel(int position, Movie movie)
        {
            Timber.d("bindModel "+movie.getOriginalTitle());
            movieTitle.setText(movie.getOriginalTitle());
            Glide.with(moviePoster.getContext())
                    .load(AppConstants.BASE_POSTER_PATH+movie.getPosterPath()).into(moviePoster);
        }
    }
}
