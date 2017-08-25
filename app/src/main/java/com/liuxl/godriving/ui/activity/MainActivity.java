package com.liuxl.godriving.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.liuxl.godriving.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Liuxl on 2017/8/25.
 */

public class MainActivity extends BaseActivity {

    Handler handler = new Handler();
    @BindView(R.id.main)
    RecyclerView main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("lxl","set tag");
              main.setTag("aaa");
            }
        },2000);

    }

    @Override
    protected void onResume() {
        super.onResume();

        startActivity(new Intent(this,SettingActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        main=null;
        Log.e("lxl","destroy");
    }
}
