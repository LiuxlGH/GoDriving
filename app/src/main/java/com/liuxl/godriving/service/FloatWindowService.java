package com.liuxl.godriving.service;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.liuxl.godriving.EventBus.FloatWindowEvent;
import com.liuxl.godriving.EventBus.RxBus;
import com.liuxl.godriving.ui.activity.MainActivity;
import com.liuxl.godriving.util.SPKit;

import io.reactivex.functions.Consumer;

/**
 * Created by 10170153 on 3/24/2017.
 */

public class FloatWindowService extends Service {
    private static final int UPDATE_PIC = 0x100;
    private int statusBarHeight;// 状态栏高度
    private Thread updateThread = null;
    private HandlerUI handler = null;
    private boolean viewAdded = false;// 透明窗体是否已经显示
    private boolean viewHide = false; // 窗口隐藏
    private WindowManager wm;
    TextView txt;
    private WindowManager.LayoutParams layoutParams;
    boolean isTxtUpdated = false;
    FloatWindowControl floatWindow;

    public boolean isPopup = true;
    String msg;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        floatWindow = new FloatWindowControl();
        RxBus.getDefault().register(FloatWindowEvent.class, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                floatWindow.showInTopWindow(getApplicationContext(),(String) o);
            }
        });
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);

        if (!SPKit.getInstance().getFloatWindowCMD()) {
            return;
        }
        msg=intent.getStringExtra("txt");
        wm = (WindowManager) getApplicationContext().getSystemService(
                Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, 0, 0,
                PixelFormat.TRANSPARENT);
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//        params.gravity = Gravity.TOP;
//        params.y = 30;
        isTxtUpdated = false;
        if (txt != null) {
            txt.setText(msg);
            isTxtUpdated = true;
        } else {
            txt = new TextView(this);
            txt.setText(msg);
            txt.setPadding(20, 30, 30, 20);
            txt.setTextColor(getResources().getColor(android.R.color.white));
            txt.getPaint().setFakeBoldText(true);
            txt.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1.0f,
                    Animation.RELATIVE_TO_SELF, 0f);
            animation.setDuration(500);
            txt.startAnimation(animation);
            wm.addView(txt, params);
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    wm.removeView(txt);
                    txt = null;
//                    MainActivity.mSpeechSynthesizer.stop();
                }
            });
        }

        viewHide = false;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        removeView();
//        RxBus.getDefault().unRegister();
    }

    /**
     * 关闭悬浮窗
     */
    public void removeView() {
        if (viewAdded) {
//            windowManager.removeView();
//            viewAdded = false;
        }
    }
    /**
     * 接受消息和处理消息
     *
     * @author Administrator
     *
     */
    class HandlerUI extends Handler {
        public HandlerUI() {

        }

        public HandlerUI(Looper looper) {
            super(looper);
        }

        /**
         * 接收消息
         */
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            // 根据收到的消息分别处理

        }

    }

    /**
     * 更新悬浮窗的信息
     *
     * @author Administrator
     *
     */
    class UpdateUI implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            // 如果没有中断就一直运行
            while (!Thread.currentThread().isInterrupted()) {
                Message msg = handler.obtainMessage();
                msg.what = UPDATE_PIC; // 设置消息标识
                handler.sendMessage(msg);
                // 休眠1s
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    class FloatWindowControl {
        WindowManager wm;
        TextView txt;
        boolean isTxtUpdated = false;

        public boolean isPopup = false;

        public FloatWindowControl(){
        }

        public void showInTopWindow(Context context,String msg) {
            if (!isPopup) {
                return;
            }

            wm = (WindowManager) getApplicationContext().getSystemService(
                    Context.WINDOW_SERVICE);
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
//
//        // 设置window type
//        params.type = WindowManager.LayoutParams.TYPE_PHONE;//TYPE_SYSTEM_ALERT  has high priority
//        /*
//         * 如果设置为params.type = WindowManager.LayoutParams.TYPE_PHONE; 那么优先级会降低一些,
//         * 即拉下通知栏不可见
//         */
//
//        params.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
//
//        // 设置Window flag
//        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        /*
//         * 下面的flags属性的效果形同“锁定”。 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
//         * wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL |
//         * LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
//         */
//
//        // 设置悬浮窗的长得宽
//        params.width = context.getResources().getDisplayMetrics().widthPixels;
//        params.height = 200;

//        // 设置悬浮窗的Touch监听
//        btn_floatView.setOnTouchListener(new OnTouchListener()
//        {
//            int lastX, lastY;
//            int paramX, paramY;
//
//            public boolean onTouch(View v, MotionEvent event)
//            {
//                switch (event.getAction())
//                {
//                    case MotionEvent.ACTION_DOWN:
//                        lastX = (int) event.getRawX();
//                        lastY = (int) event.getRawY();
//                        paramX = params.x;
//                        paramY = params.y;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        int dx = (int) event.getRawX() - lastX;
//                        int dy = (int) event.getRawY() - lastY;
//                        params.x = paramX + dx;
//                        params.y = paramY + dy;
//                        // 更新悬浮窗位置
//                        wm.updateViewLayout(btn_floatView, params);
//                        break;
//                }
//                return true;
//            }
//        });


            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, 0, 0,
                    PixelFormat.TRANSPARENT);
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            params.gravity = Gravity.TOP;
//        params.x = 100;
//        params.y = 300;

            isTxtUpdated = false;
            if (txt != null) {
                txt.setText(msg);
                isTxtUpdated = true;
            } else {
                txt = new TextView(context);
                txt.setText(msg);
                txt.setPadding(20, 30, 30, 20);
                txt.setTextColor(context.getResources().getColor(android.R.color.white));
                txt.getPaint().setFakeBoldText(true);
                txt.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
                TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1.0f,
                        Animation.RELATIVE_TO_SELF, 0f);
                animation.setDuration(500);
                txt.startAnimation(animation);
                wm.addView(txt, params);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        int count = 0;
                        while (count < 6) {
                            if (isTxtUpdated) {
                                count = 0;
                            }
                            try {
                                Thread.sleep(500);
                                count++;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        if (txt == null) {
                            return;
                        }
//                    context.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            wm.removeView(txt);
//                            txt = null;
//                        }
//                    });
                    }
                }).start();
            }
        }

    }
}