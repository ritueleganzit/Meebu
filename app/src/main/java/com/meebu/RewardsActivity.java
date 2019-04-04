package com.meebu;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.meebu.adapter.RewardsAdapter;
import com.meebu.model.RewardsData;

import java.util.ArrayList;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class RewardsActivity extends AppCompatActivity {

    RecyclerView rc_rewards;
    ImageView back;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        setContentView(R.layout.activity_rewards);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rc_rewards=findViewById(R.id.rc_rewards);
        back=findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2);
        rc_rewards.setLayoutManager(layoutManager);
        rc_rewards.setNestedScrollingEnabled(false);
        RewardsData rewardsData1=new RewardsData("","","$ 100",R.drawable.coupon_bg_3_small);
        RewardsData rewardsData2=new RewardsData("","","$ 300",R.drawable.coupon_bg_1_small);
        RewardsData rewardsData3=new RewardsData("","","$ 200",R.drawable.coupon_bg_2_small);

        ArrayList<RewardsData> arrayList=new ArrayList<>();

        arrayList.add(rewardsData1);
        arrayList.add(rewardsData2);
        arrayList.add(rewardsData3);
        arrayList.add(rewardsData1);
        arrayList.add(rewardsData2);
        arrayList.add(rewardsData3);
        arrayList.add(rewardsData1);
        arrayList.add(rewardsData2);
        arrayList.add(rewardsData3);
        arrayList.add(rewardsData1);
        arrayList.add(rewardsData2);
        arrayList.add(rewardsData3);
        arrayList.add(rewardsData1);
        arrayList.add(rewardsData2);
        arrayList.add(rewardsData3);

        rc_rewards.setAdapter(new RewardsAdapter(this,arrayList));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
