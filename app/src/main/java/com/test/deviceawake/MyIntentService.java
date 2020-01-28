package com.test.deviceawake;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2018/12/21.
 */

public class MyIntentService extends IntentService{
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle extras = intent.getExtras();
        MyWakefulReceiver.completeWakefulIntent(intent);
    }
}
