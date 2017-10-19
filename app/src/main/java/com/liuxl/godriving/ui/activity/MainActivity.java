package com.liuxl.godriving.ui.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.liuxl.godriving.EventBus.RxBus;
import com.liuxl.godriving.EventBus.SpeakerEvent;
import com.liuxl.godriving.R;
import com.liuxl.godriving.service.FloatWindowService;
import com.liuxl.godriving.service.NotificationService;
import com.liuxl.godriving.service.SpeakerService;
import com.liuxl.godriving.util.SPKit;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Liuxl on 2017/8/25.
 */

public class MainActivity extends BaseActivity {

    Handler handler = new Handler();
    @BindView(R.id.main)
    RecyclerView main;
    @BindView(R.id.btnTest)
    Button btnTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SPKit.getInstance().initSharedPreferences(this);
        initPermission();
        ButterKnife.bind(this);

        startService(new Intent(this,SpeakerService.class));
        startService(new Intent(this,FloatWindowService.class));

        if (Build.VERSION.SDK_INT >= 23) {
            if(!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(intent);
                return;
            } else {
                //绘ui代码, 这里说明6.0系统已经有权限了
            }
        } else {
            //绘ui代码,这里android6.0以下的系统直接绘出即可
        }

        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(this,NotificationService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(new ComponentName(this,NotificationService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.e("lxl", "set tag");
//                main.setTag("aaa");
//            }
//        }, 2000);

        btnTest.setOnClickListener(v -> RxBus.getDefault().post(new SpeakerEvent("123456")));
    }

    @Override
    protected void onResume() {
        super.onResume();

//        startActivity(new Intent(this, SettingActivity.class));
//        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
//        main = null;
        Log.e("lxl", "destroy");
    }

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        ArrayList<String> toApplyList = new ArrayList<String>();
        for (String perm :permissions){
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)) {

            // Show an expanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

            Log.d("lxltest","shouldShowRequestPermissionRationale");
        } else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    123);
            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
//        if (!toApplyList.isEmpty()){
//            ActivityCompat.requestPermissions(this, permissions, 123);
//        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
        Log.d("lxltest","grantResults : "+grantResults.length);
    }
}
