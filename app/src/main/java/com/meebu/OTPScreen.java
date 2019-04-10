package com.meebu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.meebu.api.ApiInterface;
import com.meebu.databinding.ActivityOtpscreenBinding;
import com.meebu.utils.RobotoRegularTextView;

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

public class OTPScreen extends AppCompatActivity {

    int time=30;
    LinearLayout lincontinue;
    String mobile="";
    RobotoRegularTextView timer,hide;
    ActivityOtpscreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding= DataBindingUtil.setContentView(OTPScreen.this,R.layout.activity_otpscreen);






        binding.lincontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(new Intent(OTPScreen.this,SignupScreen.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();*/


            }
        });

        /*linsignup=findViewById(R.id.linsignup);
        timer=findViewById(R.id.timer);
        hide=findViewById(R.id.hide);
        hide.setVisibility(View.GONE);
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("0:"+checkDigit(time));
                time--;
            }

            public void onFinish() {
                timer.setText("");
                hide.setVisibility(View.VISIBLE);
            }

        }.start();


        linsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OTPScreen.this,HomeActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();

            }
        });*/
    }


    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
