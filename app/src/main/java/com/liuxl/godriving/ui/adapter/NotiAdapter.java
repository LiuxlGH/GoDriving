package com.liuxl.godriving.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Liuxl on 2017/10/26.
 */

public class NotiAdapter extends BaseAdapter<String>{

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TextView tv = new TextView(parent.getContext());
        ViewHolder viewHolder = new ViewHolder(tv);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder curHolder = (ViewHolder) holder;
        curHolder.tv.setText(getItem(position));

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView)itemView;
        }
    }
}
