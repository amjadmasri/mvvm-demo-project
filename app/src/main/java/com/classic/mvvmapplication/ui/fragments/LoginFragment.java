package com.classic.mvvmapplication.ui.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.View;
import android.widget.Toast;

import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.databinding.LoginFragmentBinding;
import com.classic.mvvmapplication.ui.BaseFragment;
import com.classic.mvvmapplication.utilities.Resource;
import com.classic.mvvmapplication.utilities.ViewModelProviderFactory;
import com.classic.mvvmapplication.viewModels.LoginViewModel;

import javax.inject.Inject;

import timber.log.Timber;

public class LoginFragment extends BaseFragment<LoginViewModel, LoginFragmentBinding> {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private LoginViewModel loginViewModel;

    private LoginFragment.OnFragmentInteractionListener mListener;
    private NavController navController;

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
    protected int getBindingVariable() {
        return com.classic.mvvmapplication.BR.loginViewmodel;
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

        dataBinding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.themoviedb.org/account/signup");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        dataBinding.btnServerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = dataBinding.etEmail.getText().toString();
                String password = dataBinding.etPassword.getText().toString();
               attemptLogin(name,password);
            }
        });

        loginViewModel.getEmailError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dataBinding.etEmail.setError(s);
            }
        });

        loginViewModel.getPasswordError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dataBinding.etPassword.setError(s);
            }
        });

        dataBinding.textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.loginAsGuest();
            }
        });


        loginViewModel.getGetNewTokenLiveData().observe(requireActivity(), new Observer<Resource<String>>() {
            @Override
            public void onChanged(Resource<String> stringResource) {
                switch (stringResource.status){
                    case SUCCESS:
                        setStatusMessage(null);
                        setLoading(View.GONE);
                        dataBinding.btnServerLogin.setClickable(true);
                        break;
                    case ERROR:
                        setStatusMessage(stringResource.data);
                        setLoading(View.GONE);
                        Toast.makeText(requireContext(), stringResource.message, Toast.LENGTH_SHORT).show();
                        dataBinding.btnServerLogin.setClickable(true);
                        break;
                    case LOADING:
                        setStatusMessage(stringResource.data);
                        setLoading(View.VISIBLE);
                        dataBinding.btnServerLogin.setClickable(false);
                        break;
                }
            }
        });

    }

    private void attemptLogin(String name, String password) {
        if(loginViewModel.validateLogin(name,password)){
            loginViewModel.loginUser(name,password).observe(requireActivity(), new Observer<Resource<String>>() {
                @Override
                public void onChanged(Resource<String> stringResource) {
                    switch (stringResource.status){
                        case SUCCESS:
                            setStatusMessage(stringResource.data);
                            setLoading(View.GONE);
                            navigateToMain();
                            break;
                        case ERROR:
                            setStatusMessage(stringResource.data);
                            setLoading(View.GONE);
                            Toast.makeText(requireContext(), stringResource.message, Toast.LENGTH_SHORT).show();
                            break;
                        case LOADING:
                            setStatusMessage(stringResource.data);
                            setLoading(View.VISIBLE);
                            break;
                    }
                }
            });
        }
    }

    private void navigateToMain() {

        navController.navigate(R.id.action_loginFragment_to_popular_movies);
        mListener.modifyToolbarAndNavigationVisibilty(true);
    }

    private void setLoading(int visibility) {
        dataBinding.loginLoading.setVisibility(visibility);
    }

    private void setStatusMessage(String status){
        if(status!=null) {
            dataBinding.statusText.setVisibility(View.VISIBLE);
            dataBinding.statusText.setText(status);
        }
        else
            dataBinding.statusText.setVisibility(View.GONE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
    }

    public interface OnFragmentInteractionListener {
        void modifyToolbarAndNavigationVisibilty(boolean visibility);
    }

}
