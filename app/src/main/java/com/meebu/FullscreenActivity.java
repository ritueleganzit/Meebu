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
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.meebu.adapter.SearchesAdapter;
import com.meebu.model.PlaceLatLong;
import com.meebu.model.PredictionResponse;
import com.meebu.model.SearchData;
import com.meebu.utils.CallApiClient;
import com.meebu.utils.PlaceAutoCompleteInterface;
import com.meebu.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FullscreenActivity extends AppCompatActivity  implements OnMapReadyCallback, SearchesAdapter.ContactsAdapterListener {

    MapView mapView;
    RelativeLayout overlay;
    EditText ed_pickup;
    Button bt_continue;
    ImageView back;
    LinearLayout fab_layout;
    int finalHeight, finalWidth;
    private ViewGroup infoWindow;
    ProgressDialog progressDialog;
    private TextView infoSnippet;
    private OnInfoWindowElemTouchListener infoButtonListener;
    MapWrapperLayout mapWrapperLayout;
    GoogleMap googleMap;
    private String result="",city="",country="",state="";
    private String address="",postalCode="",knownName="",latitude="",longitude="";
    SessionManager sessionManager;
    CardView card_places;
    private RecyclerView rc_places;
    List<SearchData> searchList;
    List<SearchData> searchListbuffer;
    SearchesAdapter searchesAdapter;
    String userInput;
    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        sessionManager=new SessionManager(FullscreenActivity.this);

        HashMap<String,String> hashMap=sessionManager.getUserDetails();
progressDialog=new ProgressDialog(this);
progressDialog.setMessage("Please Wait");
progressDialog.setCanceledOnTouchOutside(false);
        mapView= (MapView) findViewById(R.id.mapView);
        overlay= findViewById(R.id.overlay);
        ed_pickup= findViewById(R.id.ed_pickup);
        ed_pickup.setCursorVisible(false);
        bt_continue= findViewById(R.id.bt_continue);
        back= findViewById(R.id.back);
        card_places = findViewById(R.id.card_places);
        fab_layout= findViewById(R.id.fab_layout);
        card_places = findViewById(R.id.card_places);
        rc_places = findViewById(R.id.rc_places);
        searchList = new ArrayList<>();
        searchListbuffer = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rc_places.setLayoutManager(mLayoutManager);
        rc_places.setItemAnimator(new DefaultItemAnimator());

        searchesAdapter = new SearchesAdapter(this, searchList, this);
        rc_places.setAdapter(searchesAdapter);
        mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);
        infoWindow = (ViewGroup)getLayoutInflater().inflate(R.layout.custom_snippet_popup, null);
        infoSnippet = (TextView)infoWindow.findViewById(R.id.snippet);

        bt_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FullscreenActivity.this,SendPackageActivity.class)
                .putExtra("address",""+address)
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
        ed_pickup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, int i1, int i2) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);

                            userInput=charSequence.toString();

                            PlaceAutoCompleteInterface myInterface= CallApiClient.getRetrofit().create(PlaceAutoCompleteInterface.class);
                            Call<PredictionResponse> call=myInterface.loadPredictions(ed_pickup.getText().toString());
                            call.enqueue(new Callback<PredictionResponse>() {
                                @Override
                                public void onResponse(Call<PredictionResponse> call, final Response<PredictionResponse> response) {

                                    if(response.isSuccessful()) {


                                        Toast.makeText(FullscreenActivity.this, ""+response.body().getStatus().toString(), Toast.LENGTH_SHORT).show();
                                        if(searchListbuffer.size()>0)
                                        {
                                            searchListbuffer.clear();
                                        }
                                        for(int i=0; i<response.body().getPredictions().size();i++)
                                        {
                                            Log.d("responseseeee "+i,""+response.body().getPredictions().get(i).getDescription());
                                            SearchData searchData=new SearchData(response.body().getPredictions().get(i).getPlaceId(),response.body().getPredictions().get(i).getDescription(),response.body().getPredictions().get(i).getStructuredFormatting().getMainText(),response.body().getPredictions().get(i).getReference(),"","","");

                                            searchListbuffer.add(searchData);


                                        }
                                        if (searchListbuffer.size() > 0) {
                                            Log.d("whereeeeeeeeee", "      "+searchListbuffer.size());
                                            searchList=searchListbuffer;
                                            //android.widget.Toast.makeText(CreatePostActivity.this, contactList.size()+"    if switch open suggestion", Toast.LENGTH_SHORT).show();
                                            searchesAdapter = new SearchesAdapter(FullscreenActivity.this, searchList, new SearchesAdapter.ContactsAdapterListener() {
                                                @Override
                                                public void onContactSelected(SearchData searchData) {
                                                    ed_pickup.setText("");
                                                    ed_pickup.append(searchData.getAddress()+" ");
                                                    card_places.setVisibility(View.GONE);

                                                    setMarker(searchData.getId());

                                                }
                                            });
                                            rc_places.getRecycledViewPool().clear();
                                            rc_places.setAdapter(searchesAdapter);
                                            card_places.setVisibility(View.VISIBLE);
                                            searchesAdapter.getFilter().filter(userInput);
                                            searchesAdapter.notifyDataSetChanged();
                                        } else {
                                            Log.d("whereeeeeeeeee", "      switch case '@' in else");

                                            card_places.setVisibility(View.GONE);
                                        }

                                    }
                                    else
                                    {
                                        Log.d("whereeeeeeeeee", "   "+response.errorBody());

                                    }
                                }

                                @Override
                                public void onFailure(Call<PredictionResponse> call, Throwable t) {
                                    Toast.makeText(FullscreenActivity.this, ""+t.toString(), Toast.LENGTH_SHORT).show();

                                }
                            });

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }

            @Override
            public void afterTextChanged(Editable editable) {

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
                Log.d("wwwwwhhhhh",""+finalHeight+" - "+convertPixelsToDp(finalHeight,FullscreenActivity.this)+" ");
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)ed_pickup.getLayoutParams();
                params.leftMargin = 50;
                params.rightMargin = 50;
                params.topMargin = finalHeight+50;
                params.bottomMargin = 10;
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
                            ed_pickup.setText("");
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
    public void setMarker(String place_id)
    {

        PlaceAutoCompleteInterface myInterface=CallApiClient.getRetrofit().create(PlaceAutoCompleteInterface.class);
        Call<PlaceLatLong> call=myInterface.getLatLong(place_id);
        call.enqueue(new Callback<PlaceLatLong>() {
            @Override
            public void onResponse(Call<PlaceLatLong> call, final Response<PlaceLatLong> response) {

                if (response.isSuccessful()) {
                    LatLng latLng=new LatLng(response.body().getResult().getGeometry().getLocation().getLat(),response.body().getResult().getGeometry().getLocation().getLng());
                    Log.d("ltwhereeeeeeeeee", "  success "+latLng);

                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(""+response.body().getResult().getName())
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin)));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    progressDialog.show();
                    latitude=""+latLng.latitude;
                    longitude=""+latLng.longitude;
                    getAddress(latLng.latitude,latLng.longitude);

                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f));

                }
                else
                {
                    Log.d("ltwhereeeeeeeeee", "  fail "+response.body().toString());

                }
            }

            @Override
            public void onFailure(Call<PlaceLatLong> call, Throwable t) {
                Toast.makeText(FullscreenActivity.this, ""+t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
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

                    Log.d("djadhasuid","ad"+latitude);
                    Log.d("djadhasuid","sfcrfad"+longitude);
                    getAddress(latLng.latitude,latLng.longitude);
                }

            }
        });

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {

                // Getting view from the layout file
                View v = LayoutInflater.from(FullscreenActivity.this).inflate(R.layout.custom_snippet_popup, null);

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

                            Log.d("backkkkk", "called is present");

                            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            city = addresses.get(0).getLocality();
                            state = addresses.get(0).getAdminArea();
                            country = addresses.get(0).getCountryName();
                            postalCode = addresses.get(0).getPostalCode();
                            knownName = addresses.get(0).getFeatureName(); // Only if a

                            Toast.makeText(getApplicationContext(), "" + address, Toast.LENGTH_SHORT).show();

                            result = address;
                            progressDialog.dismiss();


                            Log.d("Addressss", "" + city);

                            Log.d("backkkkk", "called is present");
                            Log.d("Addressss", "" + state);
                            Log.d("Addressss", "" + country);
                            Log.d("Addressss", "" + postalCode);
                            Log.d("Addressss", "" + knownName);
                        }
                        else {
                            Log.d("backkkkk", "called else");

                            Toast.makeText(FullscreenActivity.this, "Not available", Toast.LENGTH_SHORT).show();
                           // progressDialog.dismiss();
                            new DataLongOperationAsynchTask().execute();                                                                                                            new DataLongOperationAsynchTask().execute();


                        }
                    } catch (IOException e) {
                       // progressDialog.dismiss();
                        Toast.makeText(FullscreenActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        new DataLongOperationAsynchTask().execute();
                        e.printStackTrace();
                    }

                }
            }, 2000);
        } catch (Exception e) {
            e.printStackTrace();
           // progressDialog.dismiss();
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            new DataLongOperationAsynchTask().execute();

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
            ed_pickup.setHint("Pickup Location");
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

    @Override
    public void onContactSelected(SearchData searchData) {
        ed_pickup.setText(searchData.getAddress());
        card_places.setVisibility(View.GONE);
    }

    public class DataLongOperationAsynchTask extends AsyncTask<String,Void,String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
progressDialog.show();


        }

        @Override
        protected String[] doInBackground(String... strings) {
            String response;
            try {
                response = getLatLongByURL("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&key=AIzaSyCZJDD1Osg2A3bYeAQG6UtTD9fll8t5-IU");
                Log.d("response", "" + response);
                return new String[]{response};
            } catch (Exception e) {
                return new String[]{"error"};
            }
        }

        @Override
        protected void onPostExecute(String... strings) {
            try {
                JSONObject jsonObject = new JSONObject(strings[0]);

                JSONArray Results = jsonObject.getJSONArray("results");

                Log.d("ddddddddd", "" + jsonObject);
                Log.d("ddddddddd", "" + result.length());

                JSONObject zero = Results.getJSONObject(0);
                JSONArray address_components = zero.getJSONArray("address_components");

                for (int i = 0; i < address_components.length(); i++) {
                    JSONObject zero2 = address_components.getJSONObject(i);
                    String long_name = zero2.getString("long_name");
                    JSONArray mtypes = zero2.getJSONArray("types");
                    String Type = mtypes.getString(0);

                    if (TextUtils.isEmpty(long_name) == false || !long_name.equals(null) || long_name.length() > 0 || long_name != "") {
                        if (Type.equalsIgnoreCase("street_number")) {
                            address = long_name + " ";
                        } else if (Type.equalsIgnoreCase("route")) {
                            address = address + long_name;
                        } else if (Type.equalsIgnoreCase("sublocality")) {
                            address = long_name;
                        } else if (Type.equalsIgnoreCase("locality")) {
                            // Address2 = Address2 + long_name + ", ";
                            city = long_name;
                        } else if (Type.equalsIgnoreCase("administrative_area_level_2")) {
                            country = long_name;
                        } else if (Type.equalsIgnoreCase("administrative_area_level_1")) {
                            state = long_name;
                        } else if (Type.equalsIgnoreCase("country")) {
                            country = long_name;
                        } else if (Type.equalsIgnoreCase("postal_code")) {
                            postalCode = long_name;
                        }
                    }

                    // JSONArray mtypes = zero2.getJSONArray("types");
                    // String Type = mtypes.getString(0);
                    // Log.e(Type,long_name);


                    Log.d("Addressssapic", "" + city);

                    Log.d("backkkkk", "called is present");
                    Log.d("Addressssapist", "" + state);
                    Log.d("Addressssapic", "" + country);
                    Log.d("Addressssapiadd", "" + address);
                    Log.d("Addressssapi", "" + knownName);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

        }

    }
    public String getLatLongByURL(String requestURL) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
