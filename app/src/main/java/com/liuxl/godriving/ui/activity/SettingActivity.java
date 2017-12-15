package com.liuxl.godriving.ui.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.Button;

import com.liuxl.godriving.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Liuxl on 2017/8/25.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.btnTest)
    Button btnTest;
    @BindView(R.id.btnNotiPermission)
    Button btnNotiPermission;
    @BindView(R.id.btnPermissionMgmt)
    Button btnPermissionMgmt;
    @BindView(R.id.btnProtectedPermission)
    Button btnProtectedPermission;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        checkPermission();
        btnTest.setOnClickListener(v -> doSendTest());
        btnNotiPermission.setOnClickListener(v -> goNotiPerssion());
        btnPermissionMgmt.setOnClickListener(v->goMainager());
        btnProtectedPermission.setOnClickListener(v->goProtect());
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE};
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }
    }

    private void goNotiPerssion() {
        Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        startActivity(intent);
    }

    private void doSendTest() {
        String text = "你好，小朋友, 好久不见，你已经长大了";
//                Sender.broadcast(text);
//                FloatWindowControl.showInTopWindow(text);
//                SpeechControl.speak(text);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(SettingActivity.this);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("滴滴");
        builder.setContentText(text);
//                builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        Intent intent0 = new Intent(SettingActivity.this, SettingActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(SettingActivity.this, 1, intent0, PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pIntent);
        manager.notify(0, builder.build());
    }

    private void goMainager() {
        try {
            Intent intent = new Intent("demo.vincent.com.tiaozhuan");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goProtect() {
        try {
            Intent intent = new Intent("demo.vincent.com.tiaozhuan");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity");
            intent.setComponent(comp);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
