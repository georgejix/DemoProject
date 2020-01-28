package com.test.net;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.mplanet.testhandler.R;

@ContentView(R.layout.activity_test_net)
public class TestNetActivity extends Activity {
    private final String TAG = "TestNetActivity";

    private WifiChangedReceiver receiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Log.d(TAG, "wifi:" + networkInfo.isConnected());
        networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        Log.d(TAG, "mobile:" + networkInfo.isConnected());


        Network networks[] = manager.getAllNetworks();
        if(null != networks){
            for(Network network : networks){
                NetworkInfo n = manager.getNetworkInfo(network);
                Log.d(TAG, "" + n.getType() + n.isConnected());
            }
        }

        manager.getActiveNetworkInfo();

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new WifiChangedReceiver();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
