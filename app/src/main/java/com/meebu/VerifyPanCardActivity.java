package com.meebu;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.meebu.databinding.ActivityVerifyPanCardBinding;

public class VerifyPanCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityVerifyPanCardBinding activityVerifyPanCardBinding= DataBindingUtil.setContentView(VerifyPanCardActivity.this, R.layout.activity_verify_pan_card);

        activityVerifyPanCardBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
