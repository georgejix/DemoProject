package com.test.timer;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.mplanet.testhandler.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * timer和timertask，调用cancel()方法之后，就不能再执行了，不能被复用
 * 一个timer可以有多个timertask，timer.cancel()停止timer的所有timertask，timertask.cancel()只停止自身
 */
@ContentView(R.layout.activity_timer)
public class TimerActivity extends Activity {
    private final String TAG = "TimerActivity";

    private ViewHandler viewHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        if(null == viewHandler){
            viewHandler = new ViewHandler();
        }
        //t1();
        t2();
    }


    private void t2(){
        final CountDownTimer timer = new CountDownTimer(5000 + 500, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timer.cancel();
            }
        }).start();
    }


    MyTimerTask timerTask;
    private void t1(){
        final Timer timer = new Timer();
        timerTask = new MyTimerTask();
        final TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                if(null != viewHandler){
                    //viewHandler.sendEmptyMessage(TIMER1);
                }
                Log.d(TAG, "timer2");
            }
        };
        timer.schedule(timerTask,1000,500);//延时1s，每隔500毫秒执行一次run方法

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timerTask.cancel();
                timerTask = new MyTimerTask();
                timer.schedule(timerTask,1000,500);//延时1s，每隔500毫秒执行一次run方法
            }
        }).start();
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            Log.d(TAG, "timer1");
        }
    }

    private final int TIMER1 = 1001;
    class ViewHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case TIMER1:
                    Toast.makeText(TimerActivity.this, "timer1 " + System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
