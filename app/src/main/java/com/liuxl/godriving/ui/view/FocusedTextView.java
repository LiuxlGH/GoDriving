package com.liuxl.godriving.ui.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Liuxl on 2018/2/11.
 */

public class FocusedTextView extends android.support.v7.widget.AppCompatTextView {
    public FocusedTextView(Context context) {
        super(context);
    }

    public FocusedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
