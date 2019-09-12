package com.classic.mvvmapplication.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classic.mvvmapplication.BR;
import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.data.models.local.Movie;
import com.classic.mvvmapplication.databinding.FragmentPopularMoviesBinding;
import com.classic.mvvmapplication.ui.Adapters.MovieAdapter;
import com.classic.mvvmapplication.ui.Adapters.MoviePagedAdapter;
import com.classic.mvvmapplication.ui.BaseFragment;
import com.classic.mvvmapplication.utilities.RecyclerViewItemDecorator;
import com.classic.mvvmapplication.utilities.Resource;
import com.classic.mvvmapplication.utilities.ViewModelProviderFactory;
import com.classic.mvvmapplication.viewModels.MovieViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;


public class PopularMoviesFragment extends BaseFragment<MovieViewModel, FragmentPopularMoviesBinding> {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    @Inject
    MovieAdapter movieAdapter;
    @Inject
    Provider<GridLayoutManager> gridLayoutManager;
    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    CompositeDisposable disposable;
    @Inject
    MoviePagedAdapter moviePagedAdapter;
    @Inject
    RecyclerViewItemDecorator recyclerViewItemDecorator;

    private MovieViewModel movieViewModel;

    private OnFragmentInteractionListener mListener;
    private NavController navController;

    public PopularMoviesFragment() {
        // Required empty public constructor
    }

    public static PopularMoviesFragment newInstance() {
        PopularMoviesFragment fragment = new PopularMoviesFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finish();
            }
        });
    }

    @Override
    protected Class<MovieViewModel> getViewModel() {
        return MovieViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_popular_movies;
    }

    @Override
    protected void attachFragmentInteractionListener(Context context) {
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        dataBinding.popularMovieRecyclerView.setLayoutManager(gridLayoutManager.get());
        dataBinding.popularMovieRecyclerView.setAdapter(moviePagedAdapter);
        dataBinding.popularMovieRecyclerView.addItemDecoration(recyclerViewItemDecorator);
        ((SimpleItemAnimator) dataBinding.popularMovieRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        moviePagedAdapter.setListener(new MovieAdapter.MovieAdapterListener() {
            @Override
            public void onMovieClick(int movieId) {

                navController.navigate(PopularMoviesFragmentDirections.actionPopularMoviesToMovieDetailsFragment(movieId));
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        movieViewModel= ViewModelProviders.of(requireActivity(),viewModelProviderFactory).get(MovieViewModel.class);



        movieViewModel.getPagedMovieList().observe(this, new Observer<Resource<PagedList<Movie>>>() {
            @Override
            public void onChanged(Resource<PagedList<Movie>> movies) {
                moviePagedAdapter.submitList(movies.data);
            }
        });
    }

    @Override
    protected int getBindingVariable() {
        return BR.popularMoviesViewModel;
    }


    public interface OnFragmentInteractionListener {

    }
}
