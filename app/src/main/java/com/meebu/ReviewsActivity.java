package com.meebu;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.meebu.adapter.MyReviewPagerAdapter;
import com.meebu.databinding.ActivityReviewsBinding;
import com.meebu.fragment.MyReviewFragment;
import com.meebu.fragment.OtherFragment;

public class ReviewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityReviewsBinding binding= DataBindingUtil.setContentView(ReviewsActivity.this,R.layout.activity_reviews);


        MyReviewPagerAdapter myReviewPagerAdapter=new MyReviewPagerAdapter(getSupportFragmentManager());
        myReviewPagerAdapter.addFragments( new MyReviewFragment(),"My Review");
        myReviewPagerAdapter.addFragments( new OtherFragment(),"Other");






        binding.reviewViewpager.setAdapter(myReviewPagerAdapter);
        binding.reviewTabs.setupWithViewPager(binding.reviewViewpager);

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
