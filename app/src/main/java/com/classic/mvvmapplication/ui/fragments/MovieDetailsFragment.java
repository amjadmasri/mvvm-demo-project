package com.classic.mvvmapplication.ui.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classic.mvvmapplication.BR;
import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.databinding.MovieDetailsFragmentBinding;
import com.classic.mvvmapplication.ui.BaseFragment;
import com.classic.mvvmapplication.viewModels.MovieDetailsViewModel;
import com.classic.mvvmapplication.viewModels.MovieViewModel;

public class MovieDetailsFragment extends BaseFragment<MovieDetailsViewModel, MovieDetailsFragmentBinding> {

    private MovieDetailsViewModel mViewModel;

    public static MovieDetailsFragment newInstance() {
        return new MovieDetailsFragment();
    }

    @Override
    protected Class<MovieDetailsViewModel> getViewModel() {
        return MovieDetailsViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.movie_details_fragment;
    }

    @Override
    protected void attachFragmentInteractionListener(Context context) {

    }

    @Override
    protected int getBindingVariable() {
        return com.classic.mvvmapplication.BR.movieDetailsViewModel;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

}
