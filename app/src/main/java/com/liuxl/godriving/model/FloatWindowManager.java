package com.liuxl.godriving.model;

import android.content.Context;
import android.graphics.PixelFormat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.liuxl.godriving.eventbus.RxBus;
import com.liuxl.godriving.eventbus.SpeakerEvent;
import com.liuxl.godriving.ui.view.FocusedTextView;

/**
 * Created by Liuxl on 2017/10/20.
 */

public class FloatWindowManager {
    WindowManager wm;
    TextView txt;
    boolean isTxtUpdated = false;
    public static boolean isOpen=true;

    public void showInTopWindow(Context context, String msg) {
        if(!isOpen){
            return;
        }
        wm = (WindowManager) context.getApplicationContext().getSystemService(
                Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params;
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, 0, 0,
                PixelFormat.TRANSPARENT);
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.format = PixelFormat.TRANSLUCENT;
        params.gravity = Gravity.TOP;
        params.y = 30;
        isTxtUpdated = false;
        if (txt != null) {
            txt.setText(msg);
            isTxtUpdated = true;
        } else {
            txt = new FocusedTextView(context);
            txt.setText(msg);
            txt.setPadding(20, 30, 30, 20);
            txt.setTextColor(context.getResources().getColor(android.R.color.white));
            txt.getPaint().setFakeBoldText(true);
            txt.setSingleLine();
            txt.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//            txt.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
            TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1.0f,
                    Animation.RELATIVE_TO_SELF, 0f);
            animation.setDuration(500);
            txt.startAnimation(animation);
            wm.addView(txt, params);
        }
        txt.setOnClickListener(view -> {
            wm.removeView(txt);
            txt = null;
            Log.d("Speaker","send stop command!!");
            RxBus.getDefault().post(new SpeakerEvent(true));
        });

    }
}
