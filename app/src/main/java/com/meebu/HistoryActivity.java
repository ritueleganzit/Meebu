package com.meebu;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.meebu.adapter.MyReviewPagerAdapter;
import com.meebu.databinding.ActivityHistoryBinding;
import com.meebu.fragment.CancelledFragment;
import com.meebu.fragment.ExpiredFragment;
import com.meebu.fragment.SuccessfulHistory;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHistoryBinding historyBinding= DataBindingUtil.setContentView(HistoryActivity.this, R.layout.activity_history);

        historyBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        onBackPressed();
            }
        });
        MyReviewPagerAdapter myReviewPagerAdapter=new MyReviewPagerAdapter(getSupportFragmentManager());
        myReviewPagerAdapter.addFragments( new SuccessfulHistory(),"Successful");
        myReviewPagerAdapter.addFragments( new ExpiredFragment(),"Expired");
        myReviewPagerAdapter.addFragments( new CancelledFragment(),"Cancelled");






        historyBinding.historyViewpager.setAdapter(myReviewPagerAdapter);
        historyBinding.historyTabs.setupWithViewPager(historyBinding.historyViewpager);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}
