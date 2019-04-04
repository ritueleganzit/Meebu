package com.meebu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    @Override
    protected void onNetworkOn() {
        Toast.makeText(this, "ON", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onNetworkOff() {
        Toast.makeText(this, "OFF", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         startActivity(new Intent(MainActivity.this,SignInActivity.class));
                         overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                         finish();
                     }
                 });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
