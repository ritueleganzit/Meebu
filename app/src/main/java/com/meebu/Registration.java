package com.meebu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.meebu.api.ApiInterface;
import com.meebu.databinding.ActivityRegistrationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.meebu.api.Constant.BASEURL;

public class Registration extends AppCompatActivity {



    LinearLayout  lincontinue;
    private String TAG="RegisterationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityRegistrationBinding binding= DataBindingUtil.setContentView(Registration.this,R.layout.activity_registration);






        binding.lincontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
