package com.test.deviceawake;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2018/12/21.
 */

public class SampleBootReceiver extends BroadcastReceiver {
    private final String TAG = "SampleBootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.d(TAG, "BOOT_COMPLETED");
            // Set the alarm here.
        }
    }
}
