package com.meebu;

import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.meebu.broadcast.NetworkStateReceiver;

public abstract class BaseActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    private NetworkStateReceiver mNetworkStateReceiver;

    protected abstract void onNetworkOn();

    private AlertDialog.Builder builder;
    protected abstract void onNetworkOff();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetworkState();
    }

    public void registerNetworkState() {
        mNetworkStateReceiver = new NetworkStateReceiver();
        mNetworkStateReceiver.addListener(this);
        this.registerReceiver(mNetworkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }



    @Override
    public void networkAvailable() {
        onNetworkOn();

    }

    @Override
    public void networkUnavailable() {

        onNetworkOff();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetworkStateReceiver);
    }

}
