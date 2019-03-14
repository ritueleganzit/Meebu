package com.meebu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(3000);
                  startActivity(new Intent(MainActivity.this,MobileSignUpActivity.class));
                  overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
