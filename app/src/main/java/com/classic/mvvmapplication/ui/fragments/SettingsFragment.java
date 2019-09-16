package com.classic.mvvmapplication.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.data.prefs.PreferencesHelper;
import com.classic.mvvmapplication.utilities.Resource;
import com.classic.mvvmapplication.utilities.ViewModelProviderFactory;
import com.classic.mvvmapplication.viewModels.UserViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;


public class SettingsFragment extends PreferenceFragmentCompat {

    private OnFragmentInteractionListener mListener;

    @Inject
    PreferencesHelper preferencesHelper;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    private UserViewModel userViewModel;
    private NavController navController;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
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
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.app_settings, rootKey);


        userViewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(UserViewModel.class);
        ListPreference listPreference = findPreference("PREF_KEY_DATA_LANGUAGE");

        listPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

               preferencesHelper.setIsDirty(true);

                return true;
            }
        });

        Preference logoutPreference = findPreference("logout");
        logoutPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                handleUserLogout();
                return true;
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
    }

    private void handleUserLogout() {
        userViewModel.logoutUser().observe(requireActivity(), new Observer<Resource<String>>() {
            @Override
            public void onChanged(Resource<String> stringResource) {
                switch (stringResource.status){
                    case SUCCESS:
                        Timber.d("success "+stringResource.data);
                        navController.navigate(R.id.action_settings_to_loginFragment);
                        break;
                    case ERROR:
                        Toast.makeText(requireContext(), stringResource.message, Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        break;
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
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

    }
}
