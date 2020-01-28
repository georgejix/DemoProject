package com.test.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;

import java.util.logging.Logger;

/**
 * Created by Administrator on 2018/12/13.
 */

public class WifiChangedReceiver extends BroadcastReceiver {
    private final String TAG = "WifiChangedReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent
                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

            if (parcelableExtra == null) {
                return;
            }

            NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                if(Build.VERSION.SDK_INT >= 28){
                    WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo info = mWifiManager.getConnectionInfo();
                    info.getSSID();
                    info.getIpAddress();
                    Log.d(TAG, info.getSSID().replace("\"","") + "," + info.getIpAddress());
                }else {
                    parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
                    if (null != parcelableExtra) {
                        WifiInfo wifiInfo = (WifiInfo) parcelableExtra;
                        String ssid = wifiInfo.getSSID();
                    }
                }
            } else if (networkInfo.getState() == NetworkInfo.State.DISCONNECTED) {
            }
        }
    }
}