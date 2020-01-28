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

/**
 * Created by Administrator on 2018/7/11.
 */

public class TestHandlerFragment01 extends Fragment {
    private ViewHandler viewHandler;
    private TextView contentText;
    private final int REFRESH_VIEW = 1001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_test_handler, null);
        if(null == viewHandler){
            viewHandler = new ViewHandler();
        }
        contentText = (TextView) view.findViewById(R.id.textview_content);
        contentText.setText("before refreshView1");
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
                    viewHandler.sendEmptyMessage(REFRESH_VIEW);
                    //myHandler.sendEmptyMessage(REFRESH_VIEW);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void refreshView(){
        contentText.setText("after refreshView1");
        System.out.println("after refreshView1");
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
