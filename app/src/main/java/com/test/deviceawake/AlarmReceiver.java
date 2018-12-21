package com.test.deviceawake;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2018/12/21.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "" + System.currentTimeMillis());
    }
}
