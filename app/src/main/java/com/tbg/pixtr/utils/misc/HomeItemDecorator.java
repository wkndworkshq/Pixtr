package com.tbg.pixtr.utils.misc;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by kausthubhadhikari on 26/12/17.
 */

public class HomeItemDecorator extends RecyclerView.ItemDecoration {

    private int space;

    public HomeItemDecorator(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getAdapter().getItemCount() - 1 == parent.getChildAdapterPosition(view)) {
            outRect.bottom = space;
            outRect.top = space;
        } else if (parent.getChildAdapterPosition(view) != 0) {
            outRect.top = space;
        }

    }
}
