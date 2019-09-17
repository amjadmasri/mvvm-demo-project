package com.classic.mvvmapplication.ui.bindingModels;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.classic.mvvmapplication.data.models.local.Genre;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.data.models.local.ReviewLocal;
import com.classic.mvvmapplication.utilities.CurrencyUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReviewDetailsBindingModel extends BaseObservable {

    public final ObservableField<String> title;

    public final ObservableField<String> content;

    public final ObservableField<String> movieTitle;

    public final ObservableField<String> movieReleaseDate;

    public final ObservableField<String> movieDropUrl;


    public ReviewDetailsBindingModel() {
        title=new ObservableField<>();
        content=new ObservableField<>();
        movieTitle=new ObservableField<>();
        movieReleaseDate= new ObservableField<>();
        movieDropUrl = new ObservableField<>();
    }

    public void setReview(ReviewLocal reviewLocal){
        title.set(reviewLocal.getAuthor());
        content.set(reviewLocal.getContent());
    }

    public void setMovie(Movie movie){
        movieTitle.set(movie.getTitle());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");
        String movieDate="";
        try {
            Date movieYear=simpleDateFormat.parse(movie.getReleaseDate());
            Calendar c = Calendar.getInstance();
            c.setTime(movieYear);
            movieDate=String.valueOf(c.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
            movieDate=movie.getReleaseDate();
        }
        movieReleaseDate.set(movieDate);
        movieDropUrl.set(movie.getBackdropPath());
    }
}
