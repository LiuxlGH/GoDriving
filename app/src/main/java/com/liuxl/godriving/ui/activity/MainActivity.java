package com.liuxl.godriving.ui.activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuxl.godriving.R;
import com.liuxl.godriving.eventbus.FloatWindowEvent;
import com.liuxl.godriving.eventbus.RxBus;
import com.liuxl.godriving.model.FloatWindowManager;
import com.liuxl.godriving.model.SpeakerManager;
import com.liuxl.godriving.service.NotificationService;
import com.liuxl.godriving.ui.fragment.MainNotiFragment;
import com.liuxl.godriving.util.SPKit;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by Liuxl on 2017/8/25.
 */

public class MainActivity extends BaseActivity {

    private static final int UPDATE_PIC = 0x100;
    @BindView(R.id.spaceHolder)
    LinearLayout spaceHolder;
    @BindView(R.id.ibtnSettings)
    ImageButton ibtnSettings;
    private int statusBarHeight;// 状态栏高度
    private Thread updateThread = null;
    private boolean viewAdded = false;// 透明窗体是否已经显示
    private boolean viewHide = false; // 窗口隐藏
    private WindowManager wm;
    TextView txt;
    private WindowManager.LayoutParams layoutParams;
    boolean isTxtUpdated = false;
    FloatWindowManager floatWindow;

    public boolean isPopup = true;
    String msg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SPKit.getInstance().initSharedPreferences(this);
        initPermission();
        ButterKnife.bind(this);
        SPKit.getInstance().initSharedPreferences(this);
        SpeakerManager.getInstance().init(this);
//        startService(new Intent(this,SpeakerService.class));
//        startService(new Intent(this,FloatWindowService.class));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainNotiFragment notiFragment = MainNotiFragment.newInstance();
        fragmentTransaction.add(notiFragment, "MainNoti");
        fragmentTransaction.commitAllowingStateLoss();

        floatWindow = new FloatWindowManager();
        RxBus.getDefault().register(FloatWindowEvent.class, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                floatWindow.showInTopWindow(MainActivity.this, ((FloatWindowEvent) o).getTxt());
            }
        }, AndroidSchedulers.mainThread());

        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
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
        pm.setComponentEnabledSetting(new ComponentName(this, NotificationService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(new ComponentName(this, NotificationService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.e("lxl", "set tag");
//                main.setTag("aaa");
//            }
//        }, 2000);

        ibtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
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
//        handler.removeCallbacksAndMessages(null);
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
        for (String perm : permissions) {
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

            Log.d("lxltest", "shouldShowRequestPermissionRationale");
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
        Log.d("lxltest", "grantResults : " + grantResults.length);
    }

//    public void onClick(View v){
//        SharedPreferenceKit kit = SharedPreferenceKit.getInstance();
//        switch (v.getId()){
//            case R.id.btnTest:


//                break;
//            case R.id.btnStartBroadcast:
//                kit.saveBroadcastCMD(true);
//                break;
//            case R.id.btnStopBroadcast:
//                kit.saveBroadcastCMD(false);
//                break;
//            case R.id.btnVoiceOff:
//                kit.saveVoiceCMD(false);
//                SpeechControl.isSpeakCmd=false;
//                SpeechControl.shutdown();
//                break;
//            case R.id.btnVoiceOn:
//                kit.saveVoiceCMD(true);
//                SpeechControl.isSpeakCmd = true;
//                SpeechControl.initTTS(this);
//                break;
//            case R.id.btnFloatWindowOff:
//                kit.saveFloatWindowCMD(false);
//                break;
//            case R.id.btnFloatWindowOn:
//                kit.saveFloatWindowCMD(true);
//                break;
//            case R.id.btnOpenPermission:
//                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
//                startActivity(intent);
//                break;
//
//        }
}
