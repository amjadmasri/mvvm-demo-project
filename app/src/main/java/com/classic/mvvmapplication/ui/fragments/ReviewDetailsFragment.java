package com.classic.mvvmapplication.ui.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classic.mvvmapplication.BR;
import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.data.models.local.ReviewLocal;
import com.classic.mvvmapplication.databinding.FragmentReviewDetailsBinding;
import com.classic.mvvmapplication.ui.BaseFragment;
import com.classic.mvvmapplication.ui.bindingModels.ReviewDetailsBindingModel;
import com.classic.mvvmapplication.utilities.Resource;
import com.classic.mvvmapplication.utilities.ViewModelProviderFactory;
import com.classic.mvvmapplication.viewModels.ReviewDetailsViewModel;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewDetailsFragment extends BaseFragment<ReviewDetailsViewModel, FragmentReviewDetailsBinding> {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    ReviewDetailsViewModel reviewDetailsViewModel;

    public ReviewDetailsFragment() {
        // Required empty public constructor
    }

    public static ReviewDetailsFragment newInstance() {
        return new ReviewDetailsFragment();
    }

    @Override
    protected Class<ReviewDetailsViewModel> getViewModel() {
        return ReviewDetailsViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_review_details;
    }

    @Override
    protected void attachFragmentInteractionListener(Context context) {

    }

    @Override
    protected int getBindingVariable() {
        return com.classic.mvvmapplication.BR.viewmodel;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        reviewDetailsViewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(ReviewDetailsViewModel.class);

        int movieId = ReviewDetailsFragmentArgs.fromBundle(getArguments()).getMovieId();
        String reviewId=ReviewDetailsFragmentArgs.fromBundle(getArguments()).getReviewId();

        final ReviewDetailsBindingModel reviewDetailsBindingModel = new ReviewDetailsBindingModel();
        dataBinding.setReviewDetailsBindingModel(reviewDetailsBindingModel);

        reviewDetailsViewModel.getMovieDetails(movieId)
                .observe(this, new Observer<Resource<Movie>>() {
                    @Override
                    public void onChanged(Resource<Movie> movieResource) {
                        if(movieResource.status.equals(Resource.Status.SUCCESS)) {
                            reviewDetailsBindingModel.setMovie(movieResource.data);
                        }
                    }
                });

        reviewDetailsViewModel.getReviewDetauls(reviewId)
                .observe(this, new Observer<ReviewLocal>() {
                    @Override
                    public void onChanged(ReviewLocal reviewLocal) {
                        reviewDetailsBindingModel.setReview(reviewLocal);
                    }
                });
    }
}
