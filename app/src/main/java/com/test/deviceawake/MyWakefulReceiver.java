package com.test.deviceawake;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by Administrator on 2018/12/21.
 * completeWakefulIntent之前，会保持cpu唤醒状态
 */

public class MyWakefulReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, MyIntentService.class);
        startWakefulService(context, service);
    }
}
