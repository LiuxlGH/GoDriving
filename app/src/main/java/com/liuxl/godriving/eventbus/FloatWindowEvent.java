package com.liuxl.godriving.eventbus;

/**
 * Created by Liuxl on 2017/10/19.
 */

public class FloatWindowEvent {
    private String txt;

    public FloatWindowEvent(String text){
        txt = text;
    }

    public String getTxt() {
        return txt;
    }
}
