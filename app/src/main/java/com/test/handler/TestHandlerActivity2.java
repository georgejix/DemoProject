package com.test.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.BaseActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mplanet.testhandler.R;

@ContentView(R.layout.activity_test_handler3)
public class TestHandlerActivity2 extends BaseActivity {
    private final String TAG = "TestHandlerActivity2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testHandler();
    }

    @OnClick(value = {R.id.handler1, R.id.handler2})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.handler1:
                if(null != handler1){
                    handler1.sendEmptyMessage(1001);
                }
                break;
            case R.id.handler2:
                if(null != handler1){
                    handler1.sendEmptyMessage(1002);
                }
                break;
        }
    }

    Handler handler1;
    private void testHandler(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler1 = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Log.d(TAG, msg.what + "");
                        Looper.myLooper().quit();
                    }
                };
                Looper.loop();
                Log.d(TAG, "loop end");
            }
        }).start();
    }
}
