package com.meebu.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by eleganz on 22/8/18.
 */

public class SansSemiBoldTextView extends AppCompatTextView {

    public SansSemiBoldTextView(Context context) {
        super(context);
    }

    public SansSemiBoldTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/source-sans-pro.semibold.ttf"));
    }
}
