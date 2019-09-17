package com.classic.mvvmapplication.di.modules;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.classic.mvvmapplication.data.models.local.ReviewLocal;
import com.classic.mvvmapplication.ui.Adapters.ReviewAdapter;
import com.classic.mvvmapplication.ui.Adapters.ReviewPagedAdapter;
import com.classic.mvvmapplication.ui.main.MainActivity;
import com.classic.mvvmapplication.utilities.diffCallbacks.ReviewDiffCallBacks;

import java.util.ArrayList;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ReviewAdapterModule {

    @Provides
    ReviewAdapter providesReviewAdapter(){
        return new ReviewAdapter(new ArrayList<ReviewLocal>());
    }

    @Provides
    @Named("review")
    LinearLayoutManager provideLinearLayoutManager(MainActivity mainActivity) {
        return new LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    ReviewPagedAdapter providesReviewPagedAdapter(ReviewDiffCallBacks reviewDiffCallBacks){
        return new ReviewPagedAdapter(reviewDiffCallBacks);
    }
}
