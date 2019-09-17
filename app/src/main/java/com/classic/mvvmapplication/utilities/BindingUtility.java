package com.classic.mvvmapplication.utilities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
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

    @BindingAdapter("layout_height")
    public static void setLayoutHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("layout_width")
    public static void setLayoutWidth(View view, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        view.setLayoutParams(layoutParams);
    }
}
