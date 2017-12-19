package com.liuxl.godriving.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuxl.godriving.R;
import com.liuxl.godriving.eventbus.RxBus;
import com.liuxl.godriving.eventbus.SpeakerEvent;
import com.liuxl.godriving.ui.adapter.GDRecyclerItemDecoration;
import com.liuxl.godriving.ui.adapter.NotiAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

/**
 * Created by Liuxl on 2017/10/23.
 */

public class MainNotiFragment extends BaseFragment {
    @BindView(R.id.rvMain)
    RecyclerView rvMain;

    NotiAdapter mAdapter;
    String data[];

    public static MainNotiFragment newInstance() {
        return new MainNotiFragment();
    }

    @Override
    View getLayoutView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view  = inflater.inflate(R.layout.fragment_main_noti,container,false);
        rvMain = (RecyclerView) view.findViewById(R.id.rvMain);
        GridLayoutManager mLayoutManager=new GridLayoutManager(getContext(),1,
                GridLayoutManager.VERTICAL,false);
        rvMain.setLayoutManager(mLayoutManager);
        rvMain.addItemDecoration(new GDRecyclerItemDecoration());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter  = new NotiAdapter();
        rvMain.setAdapter(mAdapter);
        RxBus.getDefault().register(SpeakerEvent.class, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                SpeakerEvent event = (SpeakerEvent) o;
                Log.d("Speaker",event.getTxt()+"--"+event.isStop());
                if (event.isStop()) {
                } else if(event.getTxt()!=null) {
                    String txt = event.getTxt();

                    if (data != null) {
                        String[] newData = Arrays.copyOf(data, data.length + 1);
                        data = newData;
                        for (int i = data.length - 1; i > 0; i--) {
                            data[i] = data[i - 1];
                        }
                        data[0] = txt;
                    } else {
                        data = new String[]{txt};
                    }
                    mAdapter.setData(data);
                    getActivity().runOnUiThread(() -> {
                        if (rvMain.getChildCount() + 1 < data.length) {
                            mAdapter.notifyDataSetChanged();
                        }else{
                            mAdapter.notifyItemInserted(0);
                        }
                    });

                }
            }
        });
    }
}
