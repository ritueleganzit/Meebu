package com.meebu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.meebu.model.HomeData;
import com.meebu.utils.RobotoRegularTextView;
import com.meebu.utils.RubikBoldButton;

public class SignupScreen extends AppCompatActivity {

    RubikBoldButton sp_continue;

    RobotoRegularTextView  txt_signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        txt_signin=findViewById(R.id.txt_signin);
        sp_continue=findViewById(R.id.sp_continue);

        txt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupScreen.this,SignInActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                finish();
            }
        });

        sp_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupScreen.this,HomeActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}
