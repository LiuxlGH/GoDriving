package com.liuxl.godriving.eventbus;

/**
 * Created by Liuxl on 2017/10/19.
 */

public class SpeakerEvent {
    private String txt;
    private boolean isStop;

    public SpeakerEvent(String test) {
        txt = test;
        isStop = false;
    }
    public SpeakerEvent(boolean stop){isStop = stop;}

    public String getTxt() {
        return txt;
    }

    public boolean isStop() {
        return isStop;
    }
}
