package com.liuxl.godriving.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuxl.godriving.R;

/**
 * Created by Liuxl on 2017/10/26.
 */

public class NotiAdapter extends BaseAdapter<String>{

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        View line = new View(parent.getContext());
        line.setBackgroundResource(R.color.colorAccent);
        linearLayout.addView(line,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
        TextView tv = new TextView(parent.getContext());
        tv.setBackgroundResource(R.color.black);
        tv.setTextColor(parent.getContext().getResources().getColor(android.R.color.white));
        linearLayout.addView(tv);
        ViewHolder viewHolder = new ViewHolder(linearLayout);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder curHolder = (ViewHolder) holder;
        curHolder.tv.setText(getItem(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        private View line;

        public ViewHolder(View itemView) {
            super(itemView);
            if(itemView instanceof ViewGroup) {
                ViewGroup parView = (ViewGroup) itemView;
                tv = (TextView) parView.getChildAt(1);
                line = parView.getChildAt(0);
            }
        }
    }
}
