package com.meebu.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;

import java.util.logging.Logger;

/**
 * Created by eleganz on 18/3/19.
 */

public class CustomEditTextNormal extends AppCompatEditText
{

    public CustomEditTextNormal(Context context)
    {
        super(context);
        init(context);
    }

    public CustomEditTextNormal(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public CustomEditTextNormal(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }

    public void init(Context context)
    {
        try
        {
            Typeface myFont = Typeface.createFromAsset(context.getAssets(), "fonts/TitilliumWeb-Regular.ttf");

            setTypeface(myFont);
        }
        catch (Exception e)
        {
            Log.d("error",""+e.getMessage());
        }
    }
}
