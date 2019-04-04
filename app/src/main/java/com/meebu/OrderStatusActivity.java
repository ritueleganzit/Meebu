package com.meebu;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.meebu.adapter.MyReviewPagerAdapter;
import com.meebu.databinding.ActivityOrderStatusBinding;
import com.meebu.databinding.ActivityReviewsBinding;
import com.meebu.fragment.MyReviewFragment;
import com.meebu.fragment.OtherFragment;
import com.meebu.fragment.PendingOrdersFragment;
import com.meebu.fragment.RunningOrderFragment;

public class OrderStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOrderStatusBinding binding= DataBindingUtil.setContentView(OrderStatusActivity.this,R.layout.activity_order_status);

        MyReviewPagerAdapter myReviewPagerAdapter=new MyReviewPagerAdapter(getSupportFragmentManager());
        myReviewPagerAdapter.addFragments( new RunningOrderFragment(),"Running Orders");
myReviewPagerAdapter.addFragments( new PendingOrdersFragment(),"Pending Orders");






        binding.orderstatusViewpager.setAdapter(myReviewPagerAdapter);
        binding.orderstatusTabs.setupWithViewPager(binding.orderstatusViewpager);

binding.back.setOnClickListener(new View.OnClickListener() {
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
