package com.meebu;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.meebu.databinding.ActivityInviteFriendsBinding;

public class InviteFriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityInviteFriendsBinding activityInviteFriendsBinding= DataBindingUtil.setContentView(InviteFriendsActivity.this,R.layout.activity_invite_friends);

        activityInviteFriendsBinding.back.setOnClickListener(new View.OnClickListener() {
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