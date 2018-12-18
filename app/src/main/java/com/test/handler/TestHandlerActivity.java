package com.test.handler;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mplanet.testhandler.R;

public class TestHandlerActivity extends Activity {
    private FrameLayout frameLayout;
    private FragmentManager fm;
    private TextView label1;
    private TextView label2;
    private TestHandlerFragment01 f1;
    private TestHandlerFragment02 f2;
    private ViewHandler viewHandler;
    private TextView contentText;
    private final int REFRESH_VIEW = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TestActivity", "TestHandlerActivity");
        setContentView(R.layout.activity_test_handler_);
        if(null == viewHandler){
            viewHandler = new ViewHandler();
        }
        contentText = (TextView) findViewById(R.id.textview_content);
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        label1 = (TextView) findViewById(R.id.textview_label1);
        label2 = (TextView) findViewById(R.id.textview_label2);
        f1 = new TestHandlerFragment01();
        f2 = new TestHandlerFragment02();
        fm = getFragmentManager();
        fm.beginTransaction().add(R.id.framelayout, f1).add(R.id.framelayout, f2)
                .hide(f2).show(f1).commit();
        label1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction()
                        .hide(f2).show(f1).commit();
                //fm.beginTransaction().add(R.id.framelayout, f1).commit();
            }
        });
        label2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction()
                        .hide(f1).show(f2).commit();
                /*FragmentTransaction f = fm.beginTransaction();
                f.replace(R.id.framelayout, f2);
                f.addToBackStack(null);
                f.commit();*/
            }
        });
        contentText.setText("before refreshView");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    viewHandler.sendEmptyMessage(REFRESH_VIEW);
                    //myHandler.sendEmptyMessage(REFRESH_VIEW);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void refreshView(){
        contentText.setText("after refreshView");
        System.out.println("after refreshView");
    }

    class ViewHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case REFRESH_VIEW:
                    refreshView();
                    break;
            }
        }
    }


}
