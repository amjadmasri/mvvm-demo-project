package com.classic.mvvmapplication.utilities;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class BindingUtility {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(AppConstants.BASE_POSTER_PATH+url).into(imageView);
    }

    @BindingAdapter("backdropImageUrl")
    public static void setBackdropImageUrl(ImageView imageView,String url){
        Context context = imageView.getContext();
        Glide.with(context).load(AppConstants.BASE_BACKDROP_PATH+url).into(imageView);
    }

    @BindingAdapter("youtubeUrl")
    public static void setYoutubeImageUrl(ImageView imageView,String url){
        Context context = imageView.getContext();
        Glide.with(context).load(AppConstants.YOUTUBE_THUMBNAIL_URL+url+"/default.jpg").into(imageView);
    }
}
