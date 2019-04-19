package com.meebu;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.gfranks.minimal.notification.GFMinimalNotification;
import com.meebu.api.ApiInterface;
import com.meebu.databinding.ActivitySendPackageBinding;
import com.meebu.utils.RubikBoldButton;
import com.meebu.utils.SessionManager;
import com.shuhart.stepview.StepView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorFragment;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import static com.meebu.api.Constant.BASEURL;


public class SendPackageActivity extends AppCompatActivity {
    private static final String TAG ="Sendpackage" ;
    ActivitySendPackageBinding binding;
    SessionManager sessionManager;
    ProgressDialog progressDialog;
     String destination="no";
    private static final int REQUEST_IMAGE = 2;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private ArrayList<String> mSelectPath;

    String city="",country="",state="",package_id="",mediapath="",unit="cm-kg",length="",width="",height="",weight="",mode_of_transporation="car",coupon="",price="1200";
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


binding.rgUnitMeasure.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (R.id.rb_cm==checkedId)
        {
            unit="cm-kg";
        }
        else
        {
            unit="inches-pounds";
        }
    }
});

binding.packageCar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mode_of_transporation="car";
    }
});
binding.packageBike.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mode_of_transporation="bike";
    }
});
binding.packagePlane.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mode_of_transporation="plane";
    }
});
        binding.sendPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*binding.pickup.setVisibility(View.GONE);
                binding.delivery.setVisibility(View.VISIBLE);
binding.progressViewpackage.setImageResource(R.drawable.delivery_address);*/
                if (isValid())

                {
                    addpickUpLocation();
                }
            }
        });
        binding.deliverySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressViewpackage.setImageResource(R.drawable.package_detail);

                binding.delivery.setVisibility(View.GONE);
                binding.pickup.setVisibility(View.GONE);
                binding.packagedetail.setVisibility(View.VISIBLE);

                if (isValiddes())
                {
                 adddesLocation();
                }


            }
        });

binding.packageImage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        pickImage();
    }
});
        binding.pickCountry.setText(""+country);
        binding.pickFulladdress.setText(""+address);
        binding.pickState.setText(""+address);
        binding.pickCity.setText(""+city);

        binding.recCountry.setText(""+country);
        binding.recAddress.setText(""+address);
        binding.recState.setText(""+state);
        binding.recCity.setText(""+city);


binding.confirmOrder.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (isValidpackage()) {
            progressDialog.show();
            addPackageDetail();
        }
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
    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        }else {

            MultiImageSelector selector = MultiImageSelector.create(SendPackageActivity.this);
                selector.single();
                selector.showCamera(false);

            selector.origin(mSelectPath);
            selector.start(SendPackageActivity.this, REQUEST_IMAGE);
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(SendPackageActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pickImage();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE){
            if(resultCode == RESULT_OK){
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                for(String p: mSelectPath){
                    sb.append(p);
                    sb.append("\n");
                }

                binding.packageSetimg.setVisibility(View.VISIBLE);
                mediapath=""+sb.toString();

                Glide.with(SendPackageActivity.this)
                        .load(""+mediapath.trim())


                        .into(binding.packageSetimg);
Log.d("sdadad",""+mediapath);            }
        }
        else {
            binding.packageSetimg.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {

        if (binding.packagedetail.getVisibility()==View.VISIBLE)
        {
            binding.packagedetail.setVisibility(View.GONE);
            binding.delivery.setVisibility(View.VISIBLE);
            binding.pickup.setVisibility(View.GONE);
            binding.progressViewpackage.setImageResource(R.drawable.delivery_address);
        }


        else if (binding.delivery.getVisibility()==View.VISIBLE)
        {
            binding.delivery.setVisibility(View.GONE);
            binding.packagedetail.setVisibility(View.GONE);
            binding.progressViewpackage.setImageResource(R.drawable.pickup_delivery);

            binding.pickup.setVisibility(View.VISIBLE);
        }
        else {
            super.onBackPressed();
        }

    }

    public void addPackageDetail()
    {
            final StringBuilder stringBuilder=new StringBuilder();
            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(BASEURL).build();
            final ApiInterface apiInterface = restAdapter.create(ApiInterface.class);

        TypedFile typedFile=new TypedFile("multipart/form-data",new File(mediapath.trim()));
        apiInterface.package_order(package_id, unit, binding.packageLength.getText().toString(), binding.packageWidth.getText().toString(), binding.packageHeight.getText().toString()
                , binding.packageWeight.getText().toString(), mode_of_transporation, price, typedFile, new Callback<Response>() {
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

                                    Toast.makeText(SendPackageActivity.this, "Order Saved", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SendPackageActivity.this,OrderDetailActivity.class)
                                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    .putExtra("package_id",package_id)
                                    .putExtra("package_image",jsonObject1.getString("package_image"))
                                    .putExtra("pickup_address",jsonObject1.getString("pickup_address"))
                                    .putExtra("receiver_address",jsonObject1.getString("receiver_address"))
                                    .putExtra("pack_weight",jsonObject1.getString("pack_weight"))
                                    );
                                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                                }
                                else
                                {
                                    Toast.makeText(SendPackageActivity.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }catch (Exception e)
                        {
                            Toast.makeText(SendPackageActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "catch " + e.getMessage());
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(SendPackageActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "error " + error.getMessage());

                        progressDialog.dismiss();
                    }
                });



    }
    public void addpickUpLocation()
    {
        final StringBuilder stringBuilder=new StringBuilder();
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(BASEURL).build();
        final ApiInterface apiInterface = restAdapter.create(ApiInterface.class);

        Log.d("llllll",""+user_id+"--"+p_lat+" -"+p_long+" -"+fullname+"- "+mobile+"- "+country+"- "+state+" -"+city+" --"+landmark+"-- "+address);

        apiInterface.pickupLocation(user_id, p_lat, p_long, binding.pickFullname.getText().toString(), binding.pickMobile.getText().toString(), country, state, city, binding.pickLandmark.getText().toString(), binding.pickFulladdress.getText().toString(), new Callback<Response>() {
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



    public boolean isValidpackage() {


        if (binding.packageLength.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, "Please Enter Length", GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.packageLength.requestFocus();
            return false;
        }

        else  if (binding.packageWidth.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, "Please Enter Width", GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.packageWidth.requestFocus();
            return false;
        }
        else  if (binding.packageHeight.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main,"Please Enter Height", GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.packageHeight.requestFocus();
            return false;
        }else  if (binding.packageWeight.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, "Please Enter Weight", GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.packageWeight.requestFocus();
            return false;
        }else  if (!binding.packageCheckTerms.isChecked()) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, "Please Select Terms and Condition", GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();

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
