package com.meebu;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.meebu.databinding.ActivityVerificationCodeBinding;

public class VerificationCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityVerificationCodeBinding binding= DataBindingUtil.setContentView(VerificationCodeActivity.this,R.layout.activity_verification_code);

        binding.txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VerificationCodeActivity.this,SignInActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();

            }
        });
        binding.verifySignupContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VerificationCodeActivity.this,SignupScreen.class));
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
