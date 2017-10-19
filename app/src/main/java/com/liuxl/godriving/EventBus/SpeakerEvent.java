package com.liuxl.godriving.EventBus;

/**
 * Created by Liuxl on 2017/10/19.
 */

public class SpeakerEvent {
    private String txt;

    public SpeakerEvent(String test){txt = test;}

    public String getTxt() {
        return txt;
    }
}
