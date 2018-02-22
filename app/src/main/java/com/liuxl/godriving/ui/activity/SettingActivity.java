package com.liuxl.godriving.ui.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import com.liuxl.godriving.R;
import com.liuxl.godriving.model.FloatWindowManager;
import com.liuxl.godriving.model.SpeakerManager;

import java.lang.reflect.Field;
import java.util.List;

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
    @BindView(R.id.btnFloatWindow)
    Button btnFloatWindow;
    @BindView(R.id.btnSpeaker)
    Button btnSpeaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        checkPermission();
        btnTest.setOnClickListener(v -> doSendTest());
        btnNotiPermission.setOnClickListener(v -> goNotiPerssion());
        btnPermissionMgmt.setOnClickListener(v -> goMainager());
        btnProtectedPermission.setOnClickListener(v -> goProtect());
        btnFloatWindow.setText(FloatWindowManager.isOpen?formatString(R.string.txt_float_window,"Off"):formatString(R.string.txt_float_window,"On"));
        btnFloatWindow.setOnClickListener(v->switchFloatWindow());
        btnSpeaker.setText(SpeakerManager.isOpen?formatString(R.string.txt_speaker,"Off"):formatString(R.string.txt_speaker,"On"));
        btnSpeaker.setOnClickListener(v->switchSpeakerStatus());


        getTopAppInfoPackageName(this);

    }
    /**
     * 这个方法获取最近运行任何中最上面的一个应用的包名,<br>
     * 进行了api版本的判断,然后利用不同的方法获取包名,具有兼容性
     *
     * @param context
     *            上下文对象
     * @return 返回包名,如果出现异常或者获取失败返回""
     */
    public static String getTopAppInfoPackageName(Context context) {
        if (Build.VERSION.SDK_INT < 21) { // 如果版本低于22
            // 获取到activity的管理的类
            ActivityManager m = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            // 获取最近的一个运行的任务的信息
            List<ActivityManager.RunningTaskInfo> tasks = m.getRunningTasks(1);

            if (tasks != null && tasks.size() > 0) { // 如果集合不是空的

                // 返回任务栈中最上面的一个
                ActivityManager.RunningTaskInfo info = m.getRunningTasks(1).get(0);

                // 获取到应用的包名
                // String packageName =
                // info.topActivity.getPackageName();
                return info.baseActivity.getPackageName();
            } else {
                return "";
            }
        } else {

            final int PROCESS_STATE_TOP = 2;
            try {
                // 获取正在运行的进程应用的信息实体中的一个字段,通过反射获取出来
                Field processStateField = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
                // 获取所有的正在运行的进程应用信息实体对象
                List<ActivityManager.RunningAppProcessInfo> processes = ((ActivityManager) context
                        .getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses();
                // 循环所有的进程,检测某一个进程的状态是最上面,也是就最近运行的一个应用的状态的时候,就返回这个应用的包名
                for (ActivityManager.RunningAppProcessInfo process : processes) {
                    if (process.importance <= ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                            && process.importanceReasonCode == 0) {
                        int state = processStateField.getInt(process);
                        if (state == PROCESS_STATE_TOP) { // 如果这个实体对象的状态为最近的运行应用
                            String[] packname = process.pkgList;
                            // 返回应用的包名
                            return packname[0];
                        }
                    }
                }
            } catch (Exception e) {
            }
            return "";
        }
    }
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
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
            Intent intent = new Intent("");
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
            Intent intent = new Intent("");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity");
            intent.setComponent(comp);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void switchFloatWindow(){
        FloatWindowManager.isOpen=!FloatWindowManager.isOpen;
        btnFloatWindow.setText(FloatWindowManager.isOpen?formatString(R.string.txt_float_window,"Off"):formatString(R.string.txt_float_window,"On"));
    }
    private void switchSpeakerStatus(){
        SpeakerManager.isOpen=!SpeakerManager.isOpen;
        btnSpeaker.setText(SpeakerManager.isOpen?formatString(R.string.txt_speaker,"Off"):formatString(R.string.txt_speaker,"On"));
    }

    private String formatString(int resId, String value){
        return String.format(getString(resId),value);
    }
}
