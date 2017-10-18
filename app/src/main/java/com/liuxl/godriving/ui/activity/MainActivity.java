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
import com.liuxl.godriving.R;
import com.liuxl.godriving.service.NotificationService;
import com.liuxl.godriving.util.SPKit;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Liuxl on 2017/8/25.
 */

public class MainActivity extends BaseActivity implements SpeechSynthesizerListener {

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
        startTTS();

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

        btnTest.setOnClickListener(v -> mSpeechSynthesizer.speak("1385948792526"));
    }
    // 语音合成客户端
    public static SpeechSynthesizer mSpeechSynthesizer;
    // 初始化语音合成客户端并启动
    private void startTTS() {
        // 获取语音合成对象实例
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        // 设置context
        mSpeechSynthesizer.setContext(this);
        // 设置语音合成状态监听器
        mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        // 设置在线语音合成授权，需要填入从百度语音官网申请的api_key和secret_key
        String your_api_key = "mlq0DyIA3ydUYxzLSIeKyCHS";
        String your_secret_key = "86376826e9662758703b8a32d260e290";
        mSpeechSynthesizer.setApiKey(your_api_key, your_secret_key);
        // 设置离线语音合成授权，需要填入从百度语音官网申请的app_id
//        mSpeechSynthesizer.setAppId("10045784");
        // 设置语音合成文本模型文件
        //mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, "your_txt_file_path");
        // 设置语音合成声音模型文件
        //mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, "your_speech_file_path");
        // 设置语音合成声音授权文件
        //mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, "your_licence_path");
        // 获取语音合成授权信息
        AuthInfo authInfo = mSpeechSynthesizer.auth(TtsMode.MIX);
        // 判断授权信息是否正确，如果正确则初始化语音合成器并开始语音合成，如果失败则做错误处理
        if (authInfo.isSuccess()) {
            mSpeechSynthesizer.initTts(TtsMode.MIX);
        } else {
            // 授权失败
            Toast.makeText(this,"auth failed!!",Toast.LENGTH_LONG).show();
        }
    }

    public void onError(String arg0, SpeechError arg1) {
        // 监听到出错，在此添加相关操作
    }

    public void onSpeechFinish(String arg0) {
        // 监听到播放结束，在此添加相关操作
    }

    public void onSpeechProgressChanged(String arg0, int arg1) {
        // 监听到播放进度有变化，在此添加相关操作
    }

    public void onSpeechStart(String arg0) {
        // 监听到合成并播放开始，在此添加相关操作
    }

    public void onSynthesizeDataArrived(String arg0, byte[] arg1, int arg2) {
        // 监听到有合成数据到达，在此添加相关操作
    }

    public void onSynthesizeFinish(String arg0) {
        // 监听到合成结束，在此添加相关操作
    }

    public void onSynthesizeStart(String arg0) {
        // 监听到合成开始，在此添加相关操作
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
