package com.meebu;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.ortiz.touchview.TouchImageView;

public class OrderDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    ImageView orderdetail_packageimage;

    TextView orderdetail_package_id,orderdetail_sender_address,orderdetail_receiver_address,orderdetail_packageweight;

    String package_id="",sender_address="",receiver_address="",weight="",image="";
    MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        orderdetail_packageimage=findViewById(R.id.orderdetail_packageimage);
        orderdetail_package_id=findViewById(R.id.orderdetail_package_id);
        orderdetail_sender_address=findViewById(R.id.orderdetail_sender_address);
        orderdetail_receiver_address=findViewById(R.id.orderdetail_receiver_address);
        orderdetail_packageweight=findViewById(R.id.orderdetail_packageweight);



        package_id=getIntent().getStringExtra("package_id");
        sender_address=getIntent().getStringExtra("pickup_address");
        receiver_address=getIntent().getStringExtra("receiver_address");
        weight=getIntent().getStringExtra("pack_weight");
        image=getIntent().getStringExtra("package_image");

        Log.d("package_image",image);
        mapView= (MapView) findViewById(R.id.mapView);
        orderdetail_package_id.setText(""+package_id);
        orderdetail_sender_address.setText(""+sender_address);
        orderdetail_receiver_address.setText(""+receiver_address);
        orderdetail_packageweight.setText(""+weight+" kg");
        Glide.with(OrderDetailActivity.this)
                .load(image)
                .placeholder(R.mipmap.box)
                .into(orderdetail_packageimage);


        orderdetail_packageimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(OrderDetailActivity.this);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.requestWindowFeature((int) Window.FEATURE_NO_TITLE);

                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.setContentView(R.layout.full_image);

                TouchImageView imageView=dialog.findViewById(R.id.imageViewfull);

                imageView.setMaxZoom(10);

                Glide.with(OrderDetailActivity.this)
                        .load(image)
                        .placeholder(R.mipmap.box)
                        .into(imageView);
                dialog.getWindow().setDimAmount(0.6f);

                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                dialog.show();


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

        startActivity(new Intent(OrderDetailActivity.this,HomeActivity.class));
        finishAffinity();
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
