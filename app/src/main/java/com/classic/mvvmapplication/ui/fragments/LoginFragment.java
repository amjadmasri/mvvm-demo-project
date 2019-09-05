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

import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.databinding.LoginFragmentBinding;
import com.classic.mvvmapplication.ui.BaseFragment;
import com.classic.mvvmapplication.utilities.ViewModelProviderFactory;
import com.classic.mvvmapplication.viewModels.LoginViewModel;

import javax.inject.Inject;

public class LoginFragment extends BaseFragment<LoginViewModel, LoginFragmentBinding> {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private LoginViewModel loginViewModel;

    private LoginFragment.OnFragmentInteractionListener mListener;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected Class getViewModel() {
        return LoginViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.login_fragment;
    }

    @Override
    protected void attachFragmentInteractionListener(Context context) {
        if (context instanceof LoginFragment.OnFragmentInteractionListener) {
            mListener = (LoginFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginViewModel = ViewModelProviders.of(requireActivity(),viewModelProviderFactory).get(LoginViewModel.class);

    }

    public interface OnFragmentInteractionListener {

    }

}
