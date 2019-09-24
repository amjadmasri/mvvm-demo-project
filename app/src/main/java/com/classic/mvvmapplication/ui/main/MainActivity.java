package com.classic.mvvmapplication.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.classic.mvvmapplication.BR;
import com.classic.mvvmapplication.ui.fragments.LoginFragment;
import com.classic.mvvmapplication.ui.fragments.PopularMoviesFragment;
import com.classic.mvvmapplication.ui.fragments.ProfileFragment;
import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.ui.fragments.SettingsFragment;
import com.classic.mvvmapplication.SplashFragment;
import com.classic.mvvmapplication.databinding.ActivityMainBinding;
import com.classic.mvvmapplication.ui.BaseActivity;
import com.classic.mvvmapplication.utilities.ViewModelProviderFactory;
import com.classic.mvvmapplication.viewModels.MovieViewModel;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.security.ProviderInstaller;

import javax.inject.Inject;
import javax.net.ssl.SSLContext;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends BaseActivity<ActivityMainBinding, MovieViewModel> implements SplashFragment.OnFragmentInteractionListener ,
        PopularMoviesFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener, ProviderInstaller.ProviderInstallListener{

    @Inject
    DispatchingAndroidInjector<Object> androidInjector;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    CompositeDisposable disposable;

    private static final int ERROR_DIALOG_REQUEST_CODE = 1;
    private boolean retryProviderInstall;


    private MovieViewModel movieViewModel;
    private ActivityMainBinding activityMainBinding;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    public int getBindingVariable() {
        return BR.viewmodel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MovieViewModel getViewModel() {
        movieViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(MovieViewModel.class);
        return movieViewModel;
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = getViewDataBinding();

        ProviderInstaller.installIfNeededAsync(this, this);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        NavigationUI.setupWithNavController(activityMainBinding.bottomNavigationView, navController);

        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    @Override
    public void onProviderInstalled() {
        // Provider is up-to-date, app can make secure network calls.

        try {
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        }catch (Exception e){

        }
    }

    @Override
    public void onProviderInstallFailed(int errorCode, Intent recoveryIntent) {
        GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
        if (availability.isUserResolvableError(errorCode)) {
            // Recoverable error. Show a dialog prompting the user to
            // install/update/enable Google Play services.
            availability.showErrorDialogFragment(
                    this,
                    errorCode,
                    ERROR_DIALOG_REQUEST_CODE,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            // The user chose not to take the recovery action
                            onProviderInstallerNotAvailable();
                        }
                    });
        } else {
            // Google Play services is not available.
            onProviderInstallerNotAvailable();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ERROR_DIALOG_REQUEST_CODE) {
            // Adding a fragment via GoogleApiAvailability.showErrorDialogFragment
            // before the instance state is restored throws an error. So instead,
            // set a flag here, which will cause the fragment to delay until
            // onPostResume.
            retryProviderInstall = true;
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (retryProviderInstall) {
            // We can now safely retry installation.
            ProviderInstaller.installIfNeededAsync(this, this);
        }
        retryProviderInstall = false;
    }

    private void onProviderInstallerNotAvailable() {
        // This is reached if the provider cannot be updated for some reason.
        // App should consider all HTTP communication to be vulnerable, and take
        // appropriate action.
    }


    @Override
    protected void onStop() {
        super.onStop();


        disposable.clear();
    }

    @Override
    public void modifyToolbarAndNavigationVisibilty(boolean isVisible) {
        if (isVisible) {
            getSupportActionBar().show();
            activityMainBinding.bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            getSupportActionBar().hide();
            activityMainBinding.bottomNavigationView.setVisibility(View.GONE);
        }
    }
}
