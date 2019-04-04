package com.meebu.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class RubikBoldButton extends android.support.v7.widget.AppCompatButton {
    public RubikBoldButton(Context context) {
        super(context);
        init();
    }

    public RubikBoldButton(Context context,
                           @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RubikBoldButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        if (!isInEditMode()) {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/source-sans-pro.bold.ttf");
            setTypeface(typeface);
        }
    }
}
