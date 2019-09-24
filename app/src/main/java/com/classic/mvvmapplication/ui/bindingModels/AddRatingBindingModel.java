package com.classic.mvvmapplication.ui.bindingModels;

import android.view.View;
import android.widget.RatingBar;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.classic.mvvmapplication.BR;

import timber.log.Timber;

public class AddRatingBindingModel extends BaseObservable{

    private float rating;
    AddRatingItemListener addRatingItemListener;

    public AddRatingBindingModel(AddRatingItemListener addRatingItemListener) {
        rating=2.5f;
        this.addRatingItemListener=addRatingItemListener;
    }

    public void onConfirmClick() {
        addRatingItemListener.onConfirmClick(rating);
    }

    public void onCancelClick() {
        addRatingItemListener.onCancelClick();
    }

    @Bindable
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
        notifyPropertyChanged(com.classic.mvvmapplication.BR.rating);
    }

    public interface AddRatingItemListener {
        void onConfirmClick(Float review);
        void onCancelClick();
    }
}
