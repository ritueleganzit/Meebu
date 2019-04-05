package com.meebu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.gfranks.minimal.notification.GFMinimalNotification;
import com.meebu.api.ApiInterface;
import com.meebu.databinding.ActivitySendPackageBinding;
import com.meebu.utils.RubikBoldButton;
import com.meebu.utils.SessionManager;
import com.shuhart.stepview.StepView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.meebu.api.Constant.BASEURL;


public class SendPackageActivity extends AppCompatActivity {
    private static final String TAG ="Sendpackage" ;
    ActivitySendPackageBinding binding;
    SessionManager sessionManager;
    ProgressDialog progressDialog;
     String destination="no";
     String city="",country="",state="",package_id="";
    private String address="",postalCode="",knownName="",user_id="",p_lat="",p_long="",fullname="",mobile="",landmark="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(SendPackageActivity.this,R.layout.activity_send_package);
        sessionManager=new SessionManager(SendPackageActivity.this);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        HashMap<String,String> hashMap=sessionManager.getUserDetails();
        user_id=hashMap.get(SessionManager.USER_ID);
        address=getIntent().getStringExtra("address");
        city=getIntent().getStringExtra("city");
        country=getIntent().getStringExtra("country");
        state=getIntent().getStringExtra("state");
        postalCode=getIntent().getStringExtra("postalCode");
        knownName=getIntent().getStringExtra("knownName");
        p_lat=getIntent().getStringExtra("lat");
        p_long=getIntent().getStringExtra("lng");
        package_id=getIntent().getStringExtra("package_id");


        binding.sendPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.pickup.setVisibility(View.GONE);
                binding.delivery.setVisibility(View.VISIBLE);
binding.progressViewpackage.setImageResource(R.drawable.delivery_address);
            }
        });
        binding.deliverySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValiddes())
                {
                 adddesLocation();
                }


            }
        });
        binding.pickCountry.setText(""+country);
        binding.pickFulladdress.setText(""+address);
        binding.pickState.setText(""+state);
        binding.pickCity.setText(""+city);

        binding.recCountry.setText(""+country);
        binding.recAddress.setText(""+address);
        binding.recState.setText(""+state);
        binding.recCity.setText(""+city);

        binding.sendPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid())

                {
                    addpickUpLocation();
                }


                /*startActivity(new Intent(SendPackageActivity.this,DestinationMapActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);*/

            }
        });
binding.confirmOrder.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        
    }
});



        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        destination=getIntent().getStringExtra("destination");

        if (destination != null && !destination.isEmpty()) {
            // doSomething
            if (destination.equalsIgnoreCase("yes"))
            {
                binding.pickup.setVisibility(View.GONE);
                binding.delivery.setVisibility(View.VISIBLE);
                binding.progressViewpackage.setImageResource(R.drawable.delivery_address);
            }
        }

    }

    @Override
    public void onBackPressed() {

        if (binding.delivery.getVisibility()==View.VISIBLE)
        {
            binding.delivery.setVisibility(View.GONE);
            binding.progressViewpackage.setImageResource(R.drawable.pickup_delivery);

            binding.pickup.setVisibility(View.VISIBLE);
        }
        else if (binding.packagedetail.getVisibility()==View.VISIBLE)
        {
            binding.packagedetail.setVisibility(View.GONE);
            binding.delivery.setVisibility(View.VISIBLE);
            binding.progressViewpackage.setImageResource(R.drawable.delivery_address);
        }
        else {
            super.onBackPressed();
        }

    }
    public void addpickUpLocation()
    {
        final StringBuilder stringBuilder=new StringBuilder();
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(BASEURL).build();
        final ApiInterface apiInterface = restAdapter.create(ApiInterface.class);

        Log.d("llllll",""+user_id+"--"+p_lat+" -"+p_long+" -"+fullname+"- "+mobile+"- "+country+"- "+state+" -"+city+" --"+landmark+"-- "+address);

        apiInterface.pickupLocation(user_id, p_lat, p_long, binding.pickFullname.getText().toString(), binding.pickMobile.getText().toString(), country, state, city, binding.pickLandmark.getText().toString(), address, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    Log.d(TAG, "Success " + stringBuilder);
                    if (stringBuilder != null || !(stringBuilder.toString().equalsIgnoreCase(""))) {


                        progressDialog.dismiss();
                        JSONObject jsonObject = new JSONObject("" + stringBuilder);
                        if (jsonObject.getString("status").equalsIgnoreCase("1"))

                        {

                            JSONObject jsonObject1=jsonObject.getJSONObject("data");

                            startActivity(new Intent(SendPackageActivity.this,DestinationMapActivity.class)
                            .putExtra("package_id",""+jsonObject1.getString("id")));
                            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void adddesLocation()
    {
        final StringBuilder stringBuilder=new StringBuilder();
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(BASEURL).build();
        final ApiInterface apiInterface = restAdapter.create(ApiInterface.class);

        Log.d("llllll",""+package_id+"--"+p_lat+" -"+p_long+" -"+fullname+"- "+mobile+"- "+country+"- "+state+" -"+city+" --"+landmark+"-- "+address);

        apiInterface.destinationLocation(package_id, p_lat, p_long, binding.recName.getText().toString(), binding.recMobile.getText().toString(), country, state, city, binding.recLandmark.getText().toString(), address, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    Log.d(TAG, "Success " + stringBuilder);
                    if (stringBuilder != null || !(stringBuilder.toString().equalsIgnoreCase(""))) {


                        progressDialog.dismiss();
                        JSONObject jsonObject = new JSONObject("" + stringBuilder);
                        if (jsonObject.getString("status").equalsIgnoreCase("1"))

                        {

                            JSONObject jsonObject1=jsonObject.getJSONObject("data");

                            binding.progressViewpackage.setImageResource(R.drawable.package_detail);

                            binding.delivery.setVisibility(View.GONE);
                            binding.packagedetail.setVisibility(View.VISIBLE);

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public boolean isValid() {


        if (binding.pickFullname.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, getResources().getString(R.string.Please_enter_fullname), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.pickFullname.requestFocus();
            return false;
        }

        else  if (binding.pickMobile.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, getResources().getString(R.string.Please_enter_mobile), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.pickMobile.requestFocus();
            return false;
        }
        else  if (binding.pickCountry.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, getResources().getString(R.string.Please_enter_country), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.pickCountry.requestFocus();
            return false;
        }else  if (binding.pickState.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, getResources().getString(R.string.Please_enter_state), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.pickState.requestFocus();
            return false;
        }else  if (binding.pickCity.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, getResources().getString(R.string.Please_enter_city), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.pickCity.requestFocus();
            return false;
        }else  if (binding.pickFulladdress.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, getResources().getString(R.string.Please_enter_address), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.pickFulladdress.requestFocus();
            return false;
        }





        return true;
    }

    public boolean isValiddes() {


        if (binding.recName.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, getResources().getString(R.string.Please_enter_fullname), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.recName.requestFocus();
            return false;
        }

        else  if (binding.recMobile.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, getResources().getString(R.string.Please_enter_mobile), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.recMobile.requestFocus();
            return false;
        }
        else  if (binding.recCountry.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, getResources().getString(R.string.Please_enter_country), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.recCountry.requestFocus();
            return false;
        }else  if (binding.recState.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, getResources().getString(R.string.Please_enter_state), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.recState.requestFocus();
            return false;
        }else  if (binding.recCity.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, getResources().getString(R.string.Please_enter_city), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.recCity.requestFocus();
            return false;
        }else  if (binding.recAddress.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, getResources().getString(R.string.Please_enter_address), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.recAddress.requestFocus();
            return false;
        }





        return true;
    }
}
