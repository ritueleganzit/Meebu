package com.meebu.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by eleganz on 22/8/18.
 */

public class RobotoBoldTextView extends AppCompatTextView {

    public RobotoBoldTextView(Context context) {
        super(context);
    }

    public RobotoBoldTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/Rubik-Bold.ttf"));
    }
}
