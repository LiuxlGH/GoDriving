package com.liuxl.godriving.manager;

import android.content.Context;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.liuxl.godriving.eventbus.RxBus;
import com.liuxl.godriving.eventbus.SpeakerEvent;

import io.reactivex.functions.Consumer;

/**
 * Created by Liuxl on 2017/10/20.
 */

public class SpeakerManager implements SpeechSynthesizerListener {

    public void init(Context context){
        startTTS(context);
        RxBus.getDefault().register(SpeakerEvent.class, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                SpeakerEvent event = (SpeakerEvent) o;
//                if (event.isStop()) {
//                    mSpeechSynthesizer.stop();
//                } else {
                    mSpeechSynthesizer.speak(event.getTxt());
//                }
            }
        });
    }

    public SpeakerManager(){}

    public static SpeakerManager getInstance(){
        return Holder.instance;
    }
    private static class Holder{
        private static final SpeakerManager instance = new SpeakerManager();
    }
    // 语音合成客户端
    public SpeechSynthesizer mSpeechSynthesizer;
    // 初始化语音合成客户端并启动
    private void startTTS(Context context) {
        // 获取语音合成对象实例
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        // 设置context
        mSpeechSynthesizer.setContext(context);
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
//            Toast.makeText(this,"auth failed!!",Toast.LENGTH_LONG).show();
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
}
