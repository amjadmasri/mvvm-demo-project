package com.classic.mvvmapplication.utilities;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

public class RecyclerViewItemDecorator extends RecyclerView.ItemDecoration {
    private int spaceInPixels;

    @Inject
    public RecyclerViewItemDecorator(int spaceInPixels) {
        this.spaceInPixels = spaceInPixels;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = spaceInPixels;
        outRect.right = spaceInPixels;
        outRect.bottom = spaceInPixels;

        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = spaceInPixels;
        } else {
            outRect.top = 0;
        }
    }

    public void setSpaceInPixels(int spaceInPixels) {
        this.spaceInPixels = spaceInPixels;
    }
}
