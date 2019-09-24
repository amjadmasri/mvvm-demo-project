package com.classic.mvvmapplication.ui.fragments;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.widget.Toast;

import com.classic.mvvmapplication.R;
import com.classic.mvvmapplication.databinding.AddMovieRatingDialogBinding;
import com.classic.mvvmapplication.ui.bindingModels.AddRatingBindingModel;
import com.classic.mvvmapplication.utilities.Resource;
import com.classic.mvvmapplication.utilities.ViewModelProviderFactory;
import com.classic.mvvmapplication.viewModels.MovieDetailsViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddRatingFragment extends DialogFragment implements AddRatingBindingModel.AddRatingItemListener {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    MovieDetailsViewModel movieDetailsViewModel;
    private int movieId;

    public AddRatingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        movieDetailsViewModel= ViewModelProviders.of(requireActivity(),viewModelProviderFactory).get(MovieDetailsViewModel.class);

        movieId = AddRatingFragmentArgs.fromBundle(getArguments()).getMovieId();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AddRatingBindingModel addRatingBindingModel = new AddRatingBindingModel(this);

        AddMovieRatingDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.add_movie_rating_dialog, null, false);

        binding.setReview(addRatingBindingModel);

        return new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog_Alert)
                .setView(binding.getRoot())
                .create();
    }


    @Override
    public void onConfirmClick(Float review) {
        movieDetailsViewModel.addMovieRating(movieId,review)
                .observe(this, new Observer<Resource<String>>() {
                    @Override
                    public void onChanged(Resource<String> stringResource) {
                        switch (stringResource.status){
                            case LOADING:
                                Toast.makeText(getContext(), "Loading", Toast.LENGTH_SHORT).show();
                                break;
                            case SUCCESS:
                                Toast.makeText(getContext(), "movie Rated successfully", Toast.LENGTH_SHORT).show();
                                dismiss();
                                break;
                            case ERROR:
                                Toast.makeText(getContext(), "movie Rating failed "+stringResource.message, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
    }

    @Override
    public void onCancelClick() {
        this.dismiss();
    }
}
