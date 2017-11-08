package com.liuxl.godriving.ui.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
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
    @BindView(R.id.btnPermission)
    Button btnPermission;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
//        if (Build.VERSION.SDK_INT >= 23) {
//            int REQUEST_CODE_CONTACT = 101;
//            String[] permissions = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE};
//            for (String str : permissions) {
//                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
//                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
//                }
//            }
//        }
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
                Intent intent0 = new Intent(SettingActivity.this, SettingActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(SettingActivity.this, 1, intent0, PendingIntent.FLAG_ONE_SHOT);
                builder.setContentIntent(pIntent);
                manager.notify(0, builder.build());
            }
        });

        (findViewById(R.id.btnPermission)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivity(intent);
            }
        });
    }
}
