package com.classic.mvvmapplication.ui.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.classic.mvvmapplication.BR;
import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.data.models.local.VideoLocal;
import com.classic.mvvmapplication.databinding.MovieDetailsFragmentBinding;
import com.classic.mvvmapplication.ui.Adapters.VideoAdapter;
import com.classic.mvvmapplication.ui.BaseFragment;
import com.classic.mvvmapplication.ui.bindingModels.MovieDetailsBindingModel;
import com.classic.mvvmapplication.utilities.AppConstants;
import com.classic.mvvmapplication.utilities.Resource;
import com.classic.mvvmapplication.utilities.ViewModelProviderFactory;
import com.classic.mvvmapplication.viewModels.MovieDetailsViewModel;
import com.classic.mvvmapplication.viewModels.MovieViewModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

public class MovieDetailsFragment extends BaseFragment<MovieDetailsViewModel, MovieDetailsFragmentBinding> {

    private MovieDetailsViewModel mViewModel;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    @Inject
    VideoAdapter videoAdapter;
    @Inject
    Provider<LinearLayoutManager> linearLayoutManagerProvider;

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

        mViewModel.getMovieVideo(movieId).observe(requireActivity(), new Observer<Resource<List<VideoLocal>>>() {
            @Override
            public void onChanged(Resource<List<VideoLocal>> listResource) {
                if(listResource.status.equals(Resource.Status.SUCCESS)){
                   videoAdapter.setData(listResource.data);
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataBinding.detailHeader.videoRecycler.setLayoutManager(linearLayoutManagerProvider.get());
        dataBinding.detailHeader.videoRecycler.setAdapter(videoAdapter);

        videoAdapter.setVideoItemClick(new VideoAdapter.VideoItemClick() {
            @Override
            public void onVideoClicked(String youtubeURL) {
                Intent playVideoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.YOUTUBE_VIDEO_URL+youtubeURL));
                startActivity(playVideoIntent);
            }
        });
    }
}
