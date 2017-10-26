package com.liuxl.godriving.ui.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Liuxl on 2017/10/26.
 */

public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private T[] ds;
    public void setData(T[] list){
        ds = list;
    }

    @Override
    public int getItemCount() {
        if(ds==null){
            return 0;
        }
        return ds.length;
    }
}
