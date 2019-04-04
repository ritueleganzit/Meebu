package com.meebu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class RunningOrderActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    MapView mapView;

    private static final String TAG = RunningOrderActivity.class.getSimpleName();
    LinearLayout layoutBottomSheet;
    //ScrollView scroll;
    BottomSheetBehavior sheetBehavior;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor mpref;
    LinearLayout bottom;
    CoordinatorLayout coordinatorLayout;
    int bottomHeight;
    ImageView cancel_order,customer_care,back;

    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_running_order);
        sharedPreferences=getSharedPreferences("mystate",MODE_PRIVATE);
        mpref=sharedPreferences.edit();
        mapView= (MapView) findViewById(R.id.mapView);

        back = findViewById(R.id.back);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        layoutBottomSheet = findViewById(R.id.bottom_sheet);
        bottom = findViewById(R.id.bottom);
        cancel_order = findViewById(R.id.cancel_order);
        customer_care = findViewById(R.id.customer_care);
        //scroll = findViewById(R.id.scroll);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        ViewTreeObserver vto = bottom.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    bottom.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    bottom.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                bottomHeight  = bottom.getMeasuredHeight()+12;
                Log.d("wwwwwhhhhh",""+bottomHeight+" - "+convertPixelsToDp(bottomHeight,RunningOrderActivity.this)+" ");
                //Toast.makeText(RunningOrderActivity.this, layoutBottomSheet.getHeight()+"    "+bottomHeight, Toast.LENGTH_SHORT).show();

                //layoutBottomSheet.getLayoutParams().height = layoutBottomSheet.getHeight() - bottomHeight ;
                sheetBehavior.setPeekHeight(layoutBottomSheet.getHeight() - bottomHeight);

                layoutBottomSheet.requestLayout();
                sheetBehavior.onLayoutChild(coordinatorLayout, layoutBottomSheet, ViewCompat.LAYOUT_DIRECTION_LTR);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        /*sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        scroll.fullScroll(TOP);                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });*/

        cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(RunningOrderActivity.this,
                        R.style.Theme_Dialog);
                dialog.setContentView(R.layout.cancel_order_layout);
                TextView ok=dialog.findViewById(R.id.ok);
                TextView cancel=dialog.findViewById(R.id.cancel);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        customer_care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RunningOrderActivity.this,CustomerCareActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        mapView.getMapAsync(this);
        if(mapView != null)
        {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    @Override
    public void onClick(View v) {



    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RunningOrderActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getApplicationContext());

        googleMap.getUiSettings().setAllGesturesEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);

        LatLng loc2=new LatLng(23.0262,72.5242);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc2));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f));
    }
}

