package com.meebu;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.meebu.databinding.ActivityOtpchangeMobileBinding;

public class OTPChangeMobileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOtpchangeMobileBinding activityOtpchangeMobileBinding= DataBindingUtil.setContentView(OTPChangeMobileActivity.this, R.layout.activity_otpchange_mobile);

        activityOtpchangeMobileBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}
