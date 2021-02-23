package com.test.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by jix on 2018/12/21.
 * onHandleIntent执行完之后会自动结束
 * 只会存在一个实例，可以连续启动，会依次排序等待执行（一次执行一个）
 * 单一调用：onCreate()->onStart()->onHandleIntent()->onDestory()
 * 连续调用：onCreate()->onStart()->onHandleIntent()->onStart()->onHandleIntent()->onDestory();
 */

public class RSSPullService extends IntentService
{
    private final String TAG = "RSSPullService";

    public RSSPullService()
    {
        super("RSSPullService");
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId)
    {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        if (null != intent)
        {
            String dataString = intent.getDataString();
            Log.d(TAG, null == dataString ? "null" : dataString);
            long i1 = getApplicationContext().getMainLooper().getThread().getId();
            long i2 = Thread.currentThread().getId();
            Log.d(TAG, i1 + "," + i2);
            new Thread(() -> {
                for (int i = 0; i < 100; i++)
                {
                    try
                    {
                        Thread.sleep(1000);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    Log.d(TAG, i + "");
                }
            }).start();
        }
    }
}
