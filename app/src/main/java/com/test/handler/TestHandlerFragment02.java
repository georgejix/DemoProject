package com.test.handler;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mplanet.testhandler.R;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/7/11.
 */

public class TestHandlerFragment02 extends Fragment {
    private MyHandler myHandler;
    private TextView contentText;
    private final int REFRESH_VIEW = 1001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_test_handler, null);
        if(null == myHandler){
            myHandler = new MyHandler(this);
        }
        contentText = (TextView) view.findViewById(R.id.textview_content);
        contentText.setText("before refreshView2");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    myHandler.sendEmptyMessage(REFRESH_VIEW);
                    //myHandler.sendEmptyMessage(REFRESH_VIEW);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void refreshView(){
        contentText.setText("after refreshView2");
        System.out.println("after refreshView2");
    }

    private class MyHandler extends Handler {
        private final WeakReference<TestHandlerFragment02> mainActivityReference;

        public MyHandler(TestHandlerFragment02 activity) {
            mainActivityReference = new WeakReference<TestHandlerFragment02>(
                    activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TestHandlerFragment02 curActivity = mainActivityReference.get();
            if (curActivity != null) {
                switch (msg.what) {
                    case REFRESH_VIEW:
                        curActivity.refreshView();
                        break;
                }
            }
        }
    }
}
