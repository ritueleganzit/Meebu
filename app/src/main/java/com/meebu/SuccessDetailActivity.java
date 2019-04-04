package com.meebu;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.meebu.databinding.ActivitySuccessDetailBinding;

public class SuccessDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySuccessDetailBinding successDetailBinding= DataBindingUtil.setContentView(SuccessDetailActivity.this, R.layout.activity_success_detail);


        successDetailBinding.back.setOnClickListener(new View.OnClickListener() {
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
