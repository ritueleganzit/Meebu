package com.meebu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class  DestinationMapActivity extends AppCompatActivity  implements OnMapReadyCallback {

    MapView mapView;
    RelativeLayout overlay;
    EditText ed_pickup;
    Button bt_continue;
    ImageView back;
    LinearLayout fab_layout;
    int finalHeight, finalWidth;
    private ViewGroup infoWindow;
    private TextView infoSnippet;
    private OnInfoWindowElemTouchListener infoButtonListener;
    MapWrapperLayout mapWrapperLayout;
    GoogleMap googleMap;
    ProgressDialog progressDialog;
    String package_id="";
    private String result="",city="",country="",state="";
    private String address="",postalCode="",knownName="",latitude="",longitude="";
    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_destination_fullscreen);

        mapView= (MapView) findViewById(R.id.mapView);
        overlay= findViewById(R.id.overlay);
        ed_pickup= findViewById(R.id.ed_pickup);
        ed_pickup.setCursorVisible(false);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        package_id=getIntent().getStringExtra("package_id");
        bt_continue= findViewById(R.id.bt_continue);
        back= findViewById(R.id.back);
        fab_layout= findViewById(R.id.fab_layout);
        mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);
        infoWindow = (ViewGroup)getLayoutInflater().inflate(R.layout.custom_snippet_popup, null);
        infoSnippet = (TextView)infoWindow.findViewById(R.id.snippet);

        bt_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DestinationMapActivity.this,SendPackageActivity.class).putExtra("destination","yes")
                        .putExtra("address",""+address)
                        .putExtra("package_id",""+package_id)
                        .putExtra("city",""+city)
                        .putExtra("state",""+state)
                        .putExtra("country",""+country)
                        .putExtra("postalCode",""+postalCode)
                        .putExtra("knownName",""+knownName)
                        .putExtra("lat",latitude)
                        .putExtra("lng",longitude));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });
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
                Log.d("wwwwwhhhhh",""+finalHeight+" - "+convertPixelsToDp(finalHeight,DestinationMapActivity.this)+" ");
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)ed_pickup.getLayoutParams();
                params.leftMargin = 50;
                params.rightMargin = 50;
                params.topMargin = finalHeight+50;
                params.bottomMargin = 50;
                ed_pickup.setLayoutParams(params);
            }
        });
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        onBackPressed();
    }
});

        // Set up the user interaction to manually show or hide the system UI.
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

                    googleMap.getUiSettings().setAllGesturesEnabled(true);
                    googleMap.getUiSettings().setZoomGesturesEnabled(true);
                }
                else
                {
                    googleMap.getUiSettings().setAllGesturesEnabled(false);
                    googleMap.getUiSettings().setZoomGesturesEnabled(false);
                }

                ed_pickup.setCursorVisible(true);
                ed_pickup.setClickable(false);
                Drawable img = getResources().getDrawable( R.drawable.ic_left_arrow_black);
                Drawable imgr = getResources().getDrawable( R.drawable.ic_search);
                img.setBounds( 0, 0, 60, 60 );
                imgr.setBounds( 0, 0, 60, 60 );
                ed_pickup.setCompoundDrawables( img, null, imgr, null );
                ed_pickup.setHint("Search");
                ed_pickup.setHintTextColor(Color.parseColor("#FF898989"));

            }
        });


        ed_pickup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() <= (ed_pickup.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()))  {
                        // your action here
                        Drawable[] drawables = ed_pickup.getCompoundDrawables();

                        Bitmap bitmap = ((BitmapDrawable)drawables[0] ).getBitmap();
                        Bitmap bitmap2 = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_left_arrow_black)).getBitmap();

                        if(bitmap == bitmap2)
                        {
                            onBackPressed();
                            InputMethodManager inputManager = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);

                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        }

                        return true;
                    }
                }
                return false;
            }
        });

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


    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap=googleMap;
        MapsInitializer.initialize(getApplicationContext());

        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);

        LatLng loc2=new LatLng(23.0262,72.5242);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc2));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f));
        mapWrapperLayout.init(googleMap, getPixelsFromDp(this, 39 + 20));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(overlay.getAlpha()==0.0)
                {
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("40 13 St-Washington - United States of America")
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin)));
                    progressDialog.show();

                    latitude=""+latLng.latitude;
                    longitude=""+latLng.longitude;

                    getAddress(latLng.latitude,latLng.longitude);

                }

            }
        });

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {

                // Getting view from the layout file
                View v = LayoutInflater.from(DestinationMapActivity.this).inflate(R.layout.custom_snippet_popup, null);

                TextView address = (TextView) v.findViewById(R.id.snippet);
                address.setText(marker.getTitle());

                return v;
            }

            @Override
            public View getInfoContents(Marker arg0) {
                // TODO Auto-generated method stub
                return null;
            }
        });

    }
    public void getAddress(final double latitude, final double longitude)
    {

        Log.d("hkhkh","called");
        try {


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        if(geocoder.isPresent()) {
                            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            city = addresses.get(0).getLocality();
                            state = addresses.get(0).getAdminArea();
                            country = addresses.get(0).getCountryName();
                            postalCode = addresses.get(0).getPostalCode();
                            knownName = addresses.get(0).getFeatureName(); // Only if a

                            Toast.makeText(getApplicationContext(), "" + address, Toast.LENGTH_SHORT).show();

                            result = address;

                            Log.d("Addressss", "" + city);
                            Log.d("Addressss", "" + state);
                            Log.d("Addressss", "" + country);
                            Log.d("Addressss", "" + postalCode);
                            Log.d("Addressss", "" + knownName);
                            progressDialog.dismiss();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }, 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

            ed_pickup.setCursorVisible(false);
            ed_pickup.setClickable(true);
            Drawable img = getResources().getDrawable( R.drawable.ic_location);
            img.setBounds( 0, 0, 60, 60 );
            ed_pickup.setCompoundDrawables( img, null, null, null );
            ed_pickup.setHint("Destination Location");
            ed_pickup.setHintTextColor(Color.parseColor("#313131"));
            googleMap.getUiSettings().setAllGesturesEnabled(false);
            googleMap.getUiSettings().setZoomGesturesEnabled(false);

        }
        else
        {
            super.onBackPressed();
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            googleMap.getUiSettings().setAllGesturesEnabled(true);
            googleMap.getUiSettings().setZoomGesturesEnabled(true);
        }
    }
}
