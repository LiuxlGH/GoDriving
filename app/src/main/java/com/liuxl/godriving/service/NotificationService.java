package com.liuxl.godriving.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.liuxl.godriving.EventBus.FloatWindowEvent;
import com.liuxl.godriving.EventBus.RxBus;
import com.liuxl.godriving.EventBus.SpeakerEvent;
import com.liuxl.godriving.R;
import com.liuxl.godriving.ui.activity.MainActivity;


/**
 * Created by Liuxl on 2017/10/18.
 */
public class NotificationService extends NotificationListenerService {

    final String TAG = "KL";
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
// TODO Auto-generated method stub
        Log.d(TAG, "notification posted.");
        Notification mNotification = sbn.getNotification();
        if (mNotification != null) {
            Bundle extras = mNotification.extras;

            String title = extras.getString("android.title");
            String text = extras.getString("android.text");
            int userId = extras.getInt("android.originatingUserId");

            Log.d(TAG,"title: "+title);
            Log.d(TAG,"text: "+text);
            Log.d(TAG,"userId: "+userId);

            String[] strs = getResources().getStringArray(R.array.arr);
            for(String str : strs){
                if(!title.contains(str)){
                    continue;
                }

//                FloatWindowControl.showInTopWindow(this,text);
                Intent floatIntent = new Intent(this,FloatWindowService.class);
                floatIntent.putExtra("txt",text);
                startService(floatIntent);
                RxBus.getDefault().post(new SpeakerEvent(text));
                RxBus.getDefault().post(new FloatWindowEvent(text));
//                MainActivity.mSpeechSynthesizer.speak(text);
//                SpeechControl.speak(text);
//                Sender.broadcast(title+":: "+text);
                break;
            }

            Notification.Action[] mActions = mNotification.actions;

            if (mActions != null) {
                for (Notification.Action mAction : mActions) {
                    int icon = mAction.icon;
                    CharSequence actionTitle = mAction.title;
                    PendingIntent pendingIntent = mAction.actionIntent;

                }
            }
        }
    }


    @Override
    public void onNotificationRemoved(StatusBarNotification arg0) {
// TODO Auto-generated method stub

        Log.d(TAG, "notification removed.");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(new
                Intent(this,
                FloatWindowService.class));
    }
}
