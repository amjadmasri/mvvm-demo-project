package com.classic.mvvmapplication.ui.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classic.mvvmapplication.BR;
import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.data.models.local.ReviewLocal;
import com.classic.mvvmapplication.databinding.ReviewListFragmentBinding;
import com.classic.mvvmapplication.ui.Adapters.ReviewPagedAdapter;
import com.classic.mvvmapplication.ui.BaseFragment;
import com.classic.mvvmapplication.utilities.Resource;
import com.classic.mvvmapplication.utilities.ViewModelProviderFactory;
import com.classic.mvvmapplication.viewModels.ReviewListViewModel;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

public class ReviewListFragment extends BaseFragment<ReviewListViewModel, ReviewListFragmentBinding> {

    private ReviewListViewModel reviewListViewModel;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    ReviewPagedAdapter reviewPagedAdapter;
    @Inject
    @Named("review")
    Provider<LinearLayoutManager> linearLayoutManagerProvider;
    private int movieId;

    public static ReviewListFragment newInstance() {
        return new ReviewListFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        dataBinding.reviewsRecycler.setLayoutManager(linearLayoutManagerProvider.get());
        dataBinding.reviewsRecycler.setAdapter(reviewPagedAdapter);

        reviewPagedAdapter.setListener(new ReviewPagedAdapter.ReviewLocalAdapterListener() {
            @Override
            public void onReviewLocalClick(String reviewId) {
                navController.navigate(ReviewListFragmentDirections.actionReviewListFragmentToReviewDetailsFragment(movieId,reviewId));
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        reviewListViewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(ReviewListViewModel.class);

        movieId = ReviewListFragmentArgs.fromBundle(getArguments()).getMovieId();



        reviewListViewModel.getPagedReviewList(movieId).observe(this, new Observer<Resource<PagedList<ReviewLocal>>>() {

            @Override
            public void onChanged(Resource<PagedList<ReviewLocal>> pagedListResource) {
                if(pagedListResource.status.equals(Resource.Status.SUCCESS)){
                    reviewPagedAdapter.submitList(pagedListResource.data);
                }

            }
        });

    }

    @Override
    protected Class<ReviewListViewModel> getViewModel() {
        return ReviewListViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.review_list_fragment;
    }

    @Override
    protected void attachFragmentInteractionListener(Context context) {

    }

    @Override
    protected int getBindingVariable() {
        return com.classic.mvvmapplication.BR.viewmodel;
    }
}
