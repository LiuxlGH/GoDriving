package com.liuxl.godriving.model;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.liuxl.godriving.eventbus.RxBus;
import com.liuxl.godriving.eventbus.SpeakerEvent;

/**
 * Created by Liuxl on 2017/10/20.
 */

public class FloatWindowManager {
    WindowManager wm;
    TextView txt;
    boolean isTxtUpdated = false;

    public void showInTopWindow(Context context, String msg) {
        wm = (WindowManager) context.getSystemService(
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
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    wm.removeView(txt);
                    txt = null;
                    Log.d("Speaker","send stop command!!");
                    RxBus.getDefault().post(new SpeakerEvent(true));
                }
            });
        }

    }
}
