package com.meebu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity implements OnMapReadyCallback {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    MapView mapView;
    RelativeLayout overlay;
    EditText ed_pickup;
    Button bt_continue;
    ImageView back;
    LinearLayout fab_layout;
    int finalHeight, finalWidth;
    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    //private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            //mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        mVisible = true;
        //mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        mapView= (MapView) findViewById(R.id.mapView);
        overlay= findViewById(R.id.overlay);
        ed_pickup= findViewById(R.id.ed_pickup);
        bt_continue= findViewById(R.id.bt_continue);
        back= findViewById(R.id.back);
        fab_layout= findViewById(R.id.fab_layout);

        YoYo.with(Techniques.SlideOutDown)
                .duration(100)
                .repeat(0)
                .playOn(bt_continue);

        final ImageView iv = findViewById(R.id.back);
        ViewTreeObserver vto = iv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    back.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    back.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                finalHeight  = back.getMeasuredWidth()+10;
                Log.d("wwwwwhhhhh",""+finalHeight+" - "+convertPixelsToDp(finalHeight,FullscreenActivity.this)+" ");
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)ed_pickup.getLayoutParams();
                params.leftMargin = 50;
                params.rightMargin = 50;
                params.topMargin = finalHeight+50;
                params.bottomMargin = 50;
                ed_pickup.setLayoutParams(params);
            }
        });


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        ed_pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.0f);
                animation1.setDuration(300);
                animation1.setFillAfter(true);
                overlay.startAnimation(animation1);

                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        overlay.setAlpha(0.0f);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                float back_height=back.getHeight();
                back.animate()
                        .translationY(-back_height)
                        .alpha(0.0f)
                        .setDuration(200)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                back.setVisibility(View.GONE);
                            }
                        });

                final View animatedView = findViewById(R.id.ed_pickup);
                final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animatedView.getLayoutParams();
                ValueAnimator lanimator = ValueAnimator.ofInt(params.leftMargin, 20);
                ValueAnimator ranimator = ValueAnimator.ofInt(params.rightMargin, 20);
                ValueAnimator tanimator = ValueAnimator.ofInt(params.topMargin, 20);
                lanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator)
                    {
                        params.leftMargin = (Integer) valueAnimator.getAnimatedValue();

                        animatedView.requestLayout();
                    }
                });
                ranimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator)
                    {
                        params.rightMargin = (Integer) valueAnimator.getAnimatedValue();
                        animatedView.requestLayout();
                    }
                });
                tanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator)
                    {

                        params.topMargin = (Integer) valueAnimator.getAnimatedValue();
                        animatedView.requestLayout();
                    }
                });

                lanimator.setDuration(300);
                ranimator.setDuration(300);
                tanimator.setDuration(300);
                lanimator.start();
                tanimator.start();
                ranimator.start();

                if(overlay.getAlpha()==1.0)
                {
                    YoYo.with(Techniques.SlideOutDown)
                            .duration(500)
                            .repeat(0)
                            .playOn(fab_layout);

                    YoYo.with(Techniques.SlideInUp)
                            .duration(200)
                            .repeat(0)
                            .playOn(bt_continue);

                }

                ed_pickup.setFocusable(true);
                ed_pickup.setCursorVisible(true);
                ed_pickup.setClickable(false);
                Drawable img = getResources().getDrawable( R.drawable.ic_back_black);
                img.setBounds( 0, 0, 60, 60 );
                ed_pickup.setCompoundDrawables( img, null, null, null );

            }
        });

        /*Drawable[] drawables = ed_pickup.getCompoundDrawables();

        Bitmap bitmap = ((BitmapDrawable)drawables[1] ).getBitmap();
        Bitmap bitmap2 = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_back_black)).getBitmap();

        if(bitmap == bitmap2)
        {

        }*/
        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
        mapView.getMapAsync(this);
        if(mapView != null)
        {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        //mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getApplicationContext());
        /*googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setAllGesturesEnabled(true);*/

        LatLng loc2=new LatLng(23.0262,72.5242);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc2));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                toggle();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Log.d("backkkkk",""+overlay.getAlpha());
        if(overlay.getAlpha()==0.0)
        {

            overlay.setAlpha(1.0f);
            AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
            animation1.setDuration(300);
            overlay.startAnimation(animation1);

            float back_height=back.getHeight();
            back.animate()
                    .translationY(-1)
                    .alpha(1.0f)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            back.setVisibility(View.VISIBLE);
                        }
                    });


            final View animatedView = findViewById(R.id.ed_pickup);
            final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animatedView.getLayoutParams();
            ValueAnimator lanimator = ValueAnimator.ofInt(params.leftMargin, 50);
            ValueAnimator ranimator = ValueAnimator.ofInt(params.rightMargin, 50);
            ValueAnimator tanimator = ValueAnimator.ofInt(params.topMargin, 50+finalHeight);
            lanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator)
                {
                    params.leftMargin = (Integer) valueAnimator.getAnimatedValue();

                    animatedView.requestLayout();
                }
            });
            ranimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator)
                {
                    params.rightMargin = (Integer) valueAnimator.getAnimatedValue();
                    animatedView.requestLayout();
                }
            });
            tanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator)
                {

                    params.topMargin = (Integer) valueAnimator.getAnimatedValue();
                    animatedView.requestLayout();
                }
            });

            lanimator.setDuration(300);
            ranimator.setDuration(300);
            tanimator.setDuration(300);
            lanimator.start();
            tanimator.start();
            ranimator.start();


            YoYo.with(Techniques.SlideInUp)
                    .duration(400)
                    .repeat(0)
                    .playOn(fab_layout);

            YoYo.with(Techniques.SlideOutDown)
                    .duration(200)
                    .repeat(0)
                    .playOn(bt_continue);

            ed_pickup.setFocusable(false);
            ed_pickup.setCursorVisible(false);
            ed_pickup.setClickable(true);
            Drawable img = getResources().getDrawable( R.drawable.ic_location);
            img.setBounds( 0, 0, 60, 60 );
            ed_pickup.setCompoundDrawables( img, null, null, null );
        }
        else
        {
            super.onBackPressed();
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
    }
}
