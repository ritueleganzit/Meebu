package com.meebu;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.meebu.databinding.ActivitySendPackageBinding;
import com.meebu.utils.RubikBoldButton;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;


public class SendPackageActivity extends AppCompatActivity {
     ActivitySendPackageBinding binding;

     String destination="no";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(SendPackageActivity.this,R.layout.activity_send_package);

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
                binding.progressViewpackage.setImageResource(R.drawable.package_detail);

                binding.delivery.setVisibility(View.GONE);
                binding.packagedetail.setVisibility(View.VISIBLE);

            }
        });

        binding.sendPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SendPackageActivity.this,DestinationMapActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

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
}
