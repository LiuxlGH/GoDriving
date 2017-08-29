package com.liuxl.godriving.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.liuxl.godriving.R;

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
        ButterKnife.bind(this);
        startTTS();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("lxl", "set tag");
                main.setTag("aaa");
            }
        }, 2000);

        btnTest.setOnClickListener((view)->{
            // 获取语音合成授权信息
            AuthInfo authInfo = mSpeechSynthesizer.auth(TtsMode.MIX);
            if (authInfo.isSuccess()) {
                mSpeechSynthesizer.initTts(TtsMode.MIX);
                mSpeechSynthesizer.speak("1385948792526");
            } else {
                Toast.makeText(MainActivity.this,"授权失败",Toast.LENGTH_LONG).show();
            }
        });
    }
    // 语音合成客户端
    private SpeechSynthesizer mSpeechSynthesizer;
    // 初始化语音合成客户端并启动
    private void startTTS() {
        // 获取语音合成对象实例
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        // 设置context
        mSpeechSynthesizer.setContext(this);
        // 设置语音合成状态监听器
        mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        // 设置在线语音合成授权，需要填入从百度语音官网申请的api_key和secret_key
        mSpeechSynthesizer.setApiKey("your_api_key", "your_secret_key");
        // 设置离线语音合成授权，需要填入从百度语音官网申请的app_id
        mSpeechSynthesizer.setAppId("your_app_id");
        // 设置语音合成文本模型文件
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, "your_txt_file_path");
        // 设置语音合成声音模型文件
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, "your_speech_file_path");
        // 设置语音合成声音授权文件
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, "your_licence_path");
        // 获取语音合成授权信息
        AuthInfo authInfo = mSpeechSynthesizer.auth(TtsMode.MIX);
        // 判断授权信息是否正确，如果正确则初始化语音合成器并开始语音合成，如果失败则做错误处理
        if (authInfo.isSuccess()) {
            mSpeechSynthesizer.initTts(TtsMode.MIX);
            mSpeechSynthesizer.speak("1385948792526");
        } else {
            // 授权失败
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

        startActivity(new Intent(this, SettingActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        main = null;
        Log.e("lxl", "destroy");
    }
}
