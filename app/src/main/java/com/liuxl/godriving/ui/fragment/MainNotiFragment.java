package com.liuxl.godriving.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuxl.godriving.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Liuxl on 2017/10/23.
 */

public class MainNotiFragment extends BaseFragment {
    @BindView(R.id.rvMain)
    RecyclerView rvMain;


    public static MainNotiFragment newInstance() {
        return new MainNotiFragment();
    }

    @Override
    View getLayoutView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_main_noti,container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
