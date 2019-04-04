package com.meebu;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.meebu.databinding.ActivityReviewsBinding;
import com.meebu.databinding.ActivitySavedAddressBinding;

public class SavedAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySavedAddressBinding binding= DataBindingUtil.setContentView(SavedAddressActivity.this,R.layout.activity_saved_address);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.addMorePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SavedAddressActivity.this,AddNewAddressActivity
                .class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}
