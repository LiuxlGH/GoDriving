package com.liuxl.godriving.ui.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Liuxl on 2017/12/15.
 */

public class GDRecyclerItemDecoration extends RecyclerView.ItemDecoration {


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0,0,0,1);
        super.getItemOffsets(outRect, view, parent, state);
    }
}
