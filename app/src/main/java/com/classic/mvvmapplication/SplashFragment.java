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

import com.classic.mvvmapplication.databinding.FragmentSplashBinding;
import com.classic.mvvmapplication.ui.BaseFragment;
import com.classic.mvvmapplication.utilities.ViewModelProviderFactory;
import com.classic.mvvmapplication.viewModels.UserViewModel;

import javax.inject.Inject;

import timber.log.Timber;


public class SplashFragment extends BaseFragment<UserViewModel, FragmentSplashBinding> {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    UserViewModel userViewModel;

    private OnFragmentInteractionListener mListener;
    private NavController navController;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getBindingVariable() {
        return BR.userViewModel;
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
            public void onChanged(final Boolean aBoolean) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mListener!=null)
                        handleNavigation(aBoolean);

                    }
                },2000);

            }
        });
    }

    private void handleNavigation(Boolean isUserExist) {
        if(isUserExist){
            navController.navigate(R.id.popular_movies);
            mListener.modifyToolbarAndNavigationVisibilty(true);
        }
        else{
            navController.navigate(R.id.action_splashFragment_to_loginFragment);
        }
    }


    @Override
    protected Class getViewModel() {
        return UserViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_splash;
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void modifyToolbarAndNavigationVisibilty(boolean visibility);
    }
}
