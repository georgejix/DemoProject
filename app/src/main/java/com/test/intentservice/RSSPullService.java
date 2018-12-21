package com.test.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2018/12/21.
 * onHandleIntent执行完之后会自动结束
 */

public class RSSPullService extends IntentService {
    private final String TAG = "RSSPullService";

    public RSSPullService() {
        super("RSSPullService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(null != intent) {
            String dataString = intent.getDataString();
            Log.d(TAG, null == dataString ? "null" : dataString);
            long i1 = getApplicationContext().getMainLooper().getThread().getId();
            long i2 = Thread.currentThread().getId();
            Log.d(TAG, i1 + "," + i2);
        }
    }
}
