package com.classic.mvvmapplication.ui.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.Toast;

import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.data.models.local.Genre;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.data.models.local.ReviewLocal;
import com.classic.mvvmapplication.data.models.local.VideoLocal;
import com.classic.mvvmapplication.databinding.MovieDetailsFragmentBinding;
import com.classic.mvvmapplication.ui.Adapters.MovieAdapter;
import com.classic.mvvmapplication.ui.Adapters.ReviewAdapter;
import com.classic.mvvmapplication.ui.Adapters.VideoAdapter;
import com.classic.mvvmapplication.ui.BaseFragment;
import com.classic.mvvmapplication.ui.bindingModels.MovieDetailsBindingModel;
import com.classic.mvvmapplication.utilities.AppConstants;
import com.classic.mvvmapplication.utilities.Resource;
import com.classic.mvvmapplication.utilities.ViewModelProviderFactory;
import com.classic.mvvmapplication.viewModels.MovieDetailsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import co.lujun.androidtagview.TagView;
import timber.log.Timber;

public class MovieDetailsFragment extends BaseFragment<MovieDetailsViewModel, MovieDetailsFragmentBinding> {

    private MovieDetailsViewModel mViewModel;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    @Inject
    VideoAdapter videoAdapter;
    @Inject
    ReviewAdapter reviewAdapter;
    @Inject
    @Named("video_horizontal_manager")
    Provider<LinearLayoutManager> linearLayoutManagerProvider;

    @Inject
    MovieAdapter similarMovieAdapter;

    @Inject
    @Named("review")
    Provider<LinearLayoutManager> reviewLinearLayoutManager;

    private HashMap<String, Integer> genreMap;

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

        mViewModel.getMovieDetails(movieId).observe(this, new Observer<Resource<Movie>>() {
            @Override
            public void onChanged(Resource<Movie> movieResource) {
                if(movieResource.status.equals(Resource.Status.SUCCESS)){
                    MovieDetailsBindingModel movieDetailsBindingModel = new MovieDetailsBindingModel(movieResource.data);
                    dataBinding.setMovieBindingItem(movieDetailsBindingModel);
                    setupGenreLayout(movieResource.data.getGenres());

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

        mViewModel.getMovieVideo(movieId).observe(this, new Observer<Resource<List<VideoLocal>>>() {
            @Override
            public void onChanged(Resource<List<VideoLocal>> listResource) {
                if(listResource.status.equals(Resource.Status.SUCCESS)){
                   videoAdapter.setData(listResource.data);
                   if(listResource.data.size()>0){
                       dataBinding.detailHeader.videosLabel.setVisibility(View.VISIBLE);
                   }
                }
            }
        });

        mViewModel.getTwoMovieReview(movieId).observe(this, new Observer<Resource<List<ReviewLocal>>>() {
            @Override
            public void onChanged(Resource<List<ReviewLocal>> listResource) {
                if (listResource.status.equals(Resource.Status.SUCCESS)) {
                    ArrayList<ReviewLocal> reviewLocalArrayList = new ArrayList<>(listResource.data);
                    reviewAdapter.setData(reviewLocalArrayList);
                    if(reviewLocalArrayList.size()>0) {
                        dataBinding.allReviewsTitle.setVisibility(View.VISIBLE);
                        dataBinding.reviewsTitle.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        mViewModel.getSimilarMoviesById(movieId).observe(this, new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(Resource<List<Movie>> listResource) {
                if (listResource.status.equals(Resource.Status.SUCCESS)) {
                    ArrayList<Movie> movieList = new ArrayList<>(listResource.data);
                    similarMovieAdapter.setData(movieList);
                    if(movieList.size()>0) {
                        dataBinding.similarMovieLabel.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void setupGenreLayout(List<Genre> genres) {
        genreMap = new HashMap<>();
        ArrayList<String> genresNames=new ArrayList<>();
        for (Genre genre:genres) {
            genreMap.put(genre.getName(),genre.getId());
            genresNames.add(genre.getName());
        }
        dataBinding.detailHeader.tagContainerLayout.setTags(genresNames);

        dataBinding.detailHeader.tagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                //handle to genreView navigation by using the genreMap
                int id =genreMap.get(text);
                //navigate by id
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onSelectedTagDrag(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataBinding.detailHeader.videoRecycler.setLayoutManager(linearLayoutManagerProvider.get());
        dataBinding.detailHeader.videoRecycler.setAdapter(videoAdapter);

        dataBinding.topReviewRecycler.setLayoutManager(reviewLinearLayoutManager.get());
        dataBinding.topReviewRecycler.setAdapter(reviewAdapter);

        similarMovieAdapter.setIsHorizontal(true);
        dataBinding.similarMoviesRecycler.setLayoutManager(linearLayoutManagerProvider.get());
        dataBinding.similarMoviesRecycler.setAdapter(similarMovieAdapter);


        videoAdapter.setVideoItemClick(new VideoAdapter.VideoItemClick() {
            @Override
            public void onVideoClicked(String youtubeURL) {
                Intent playVideoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.YOUTUBE_VIDEO_URL+youtubeURL));
                startActivity(playVideoIntent);
            }
        });

        reviewAdapter.setReviewItemClick(new ReviewAdapter.ReviewItemClick() {
            @Override
            public void onReviewClicked(String youtubeURL) {
                Timber.d("go to review details ");
            }
        });

        similarMovieAdapter.setListener(new MovieAdapter.MovieAdapterListener() {
            @Override
            public void onMovieClick(int MovieId) {
                navController.navigate(MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(MovieId));
            }
        });
    }
}
