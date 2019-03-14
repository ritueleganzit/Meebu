package com.meebu;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.meebu.utils.RobotoRegularTextView;

public class OTPScreen extends AppCompatActivity {

    int time=30;
    LinearLayout lincontinue;
    RobotoRegularTextView timer,hide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpscreen);
        lincontinue=findViewById(R.id.lincontinue);

        lincontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OTPScreen.this,SignupScreen.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
        /*linsignup=findViewById(R.id.linsignup);
        timer=findViewById(R.id.timer);
        hide=findViewById(R.id.hide);
        hide.setVisibility(View.GONE);
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("0:"+checkDigit(time));
                time--;
            }

            public void onFinish() {
                timer.setText("");
                hide.setVisibility(View.VISIBLE);
            }

        }.start();


        linsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OTPScreen.this,HomeActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();

            }
        });*/
    }
    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
