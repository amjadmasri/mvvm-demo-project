package com.classic.mvvmapplication.ui.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.classic.mvvmapplication.BR;
import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.databinding.MovieDetailsFragmentBinding;
import com.classic.mvvmapplication.ui.BaseFragment;
import com.classic.mvvmapplication.ui.bindingModels.MovieDetailsBindingModel;
import com.classic.mvvmapplication.utilities.Resource;
import com.classic.mvvmapplication.utilities.ViewModelProviderFactory;
import com.classic.mvvmapplication.viewModels.MovieDetailsViewModel;
import com.classic.mvvmapplication.viewModels.MovieViewModel;

import javax.inject.Inject;

public class MovieDetailsFragment extends BaseFragment<MovieDetailsViewModel, MovieDetailsFragmentBinding> {

    private MovieDetailsViewModel mViewModel;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

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
        mViewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(MovieDetailsViewModel.class);

        int movieId = MovieDetailsFragmentArgs.fromBundle(getArguments()).getMovieId();

        mViewModel.getMovieDetails(movieId).observe(requireActivity(), new Observer<Resource<Movie>>() {
            @Override
            public void onChanged(Resource<Movie> movieResource) {
                if(movieResource.status.equals(Resource.Status.SUCCESS)){
                    MovieDetailsBindingModel movieDetailsBindingModel = new MovieDetailsBindingModel(movieResource.data);
                    dataBinding.setMovieBindingItem(movieDetailsBindingModel);

                    dataBinding.detailsLoading.setVisibility(View.GONE);
                    dataBinding.detailHeader.detailsLayout.setVisibility(View.VISIBLE);
                }
                else if (movieResource.status.equals(Resource.Status.LOADING)){
                    dataBinding.detailsLoading.setVisibility(View.VISIBLE);
                    dataBinding.detailHeader.detailsLayout.setVisibility(View.INVISIBLE);
                }
                else if (movieResource.status.equals(Resource.Status.ERROR)){
                    dataBinding.detailsLoading.setVisibility(View.GONE);
                    dataBinding.detailHeader.detailsLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), movieResource.message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
