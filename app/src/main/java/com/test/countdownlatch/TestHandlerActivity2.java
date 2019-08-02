package com.test.countdownlatch;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mplanet.testhandler.R;

import java.util.concurrent.CountDownLatch;

public class TestHandlerActivity2 extends Activity {
    private final String TAG = "TestHandlerActivity2";

    private ViewHandler viewHandler;
    private CountDownLatch countDownLatch;
    private int i = 0;

    private final int MESSAGE1 = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_handler2);
        if(null == viewHandler){
            viewHandler = new ViewHandler();
        }
        countDownLatch = new CountDownLatch(1);
        for(i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        //Message msg = new Message();
                        Message msg = viewHandler.obtainMessage();
                        msg.what = MESSAGE1;
                        Bundle bundle = new Bundle();
                        bundle.putInt("num", i);
                        msg.setData(bundle);
                        Log.d(TAG, "before sendMessage");
                        countDownLatch.await();
                        //Thread.sleep(200);
                        Log.d(TAG, "sendMessage");
                        viewHandler.sendMessage(msg);
                        /*Message msg = viewHandler.obtainMessage();
                        msg.what = MESSAGE1;
                        Bundle bundle = new Bundle();
                        bundle.putInt("num", i);
                        msg.setData(bundle);
                        viewHandler.removeMessages(MESSAGE1);
                        viewHandler.sendMessageDelayed(msg, 1000);*/
                    } catch (Exception e) {
                        Log.d(TAG, e + "");
                    }
                }
            }).start();
        }

        countDownLatch = new CountDownLatch(1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        Message msg = new Message();
                        msg.what = MESSAGE1;
                        Bundle bundle = new Bundle();
                        bundle.putInt("2num", i);
                        msg.setData(bundle);
                        Log.d(TAG, "2before sendMessage");
                        countDownLatch.await();
                        //Thread.sleep(200);
                        Log.d(TAG, "2sendMessage");
                        viewHandler.sendMessage(msg);
                    } catch (Exception e) {
                        Log.d(TAG, e + "");
                    }
                }
            }).start();
        }

        countDownLatch.countDown();
    }

    class ViewHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE1:
                    //removeMessages(MESSAGE1);
                    /*try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    Log.d(TAG, "num=" + msg.getData().getInt("num"));
                    break;
            }
        }
    }
}
