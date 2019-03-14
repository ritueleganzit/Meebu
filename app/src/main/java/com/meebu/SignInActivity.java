package com.meebu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.meebu.utils.RobotoBoldTextView;
import com.meebu.utils.RobotoRegularTextView;
import com.meebu.utils.RubikBoldButton;

public class SignInActivity extends AppCompatActivity {

    RobotoRegularTextView signin;
    RobotoBoldTextView forgot;
    RubikBoldButton signin_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signin=findViewById(R.id.signin);
        signin_btn=findViewById(R.id.signin_btn);
        forgot=findViewById(R.id.forgot);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,MobileSignUpActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();

            }
        });
        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,HomeActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,ForgotPassword.class));
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
