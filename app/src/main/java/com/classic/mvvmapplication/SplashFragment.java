package com.classic.mvvmapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.classic.mvvmapplication.ui.BaseFragment;
import com.classic.mvvmapplication.utilities.ViewModelProviderFactory;
import com.classic.mvvmapplication.viewModels.UserViewModel;

import javax.inject.Inject;

import timber.log.Timber;


public class SplashFragment extends BaseFragment {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    UserViewModel userViewModel;

    private OnFragmentInteractionListener mListener;
    private NavController navController;

    public SplashFragment() {
        // Required empty public constructor
    }

    public static SplashFragment newInstance() {
        SplashFragment fragment = new SplashFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d("factory is "+(viewModelProviderFactory==null));
        userViewModel = ViewModelProviders.of(requireActivity(),viewModelProviderFactory).get(UserViewModel.class);

        checkIfUserExist();

        mListener.modifyToolbarAndNavigationVisibilty(false);

        navController = Navigation.findNavController(view);
    }

    private void checkIfUserExist() {
        userViewModel.checkIfUserExist().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        navController.navigate(R.id.popular_movies);
                        mListener.modifyToolbarAndNavigationVisibilty(true);
                    }
                },2000);

            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void modifyToolbarAndNavigationVisibilty(boolean visibility);
    }
}
