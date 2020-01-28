package com.test.touchevent;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2018/12/20.
 */

public class MyLinearLayour extends LinearLayout {
    public MyLinearLayour(Context context) {
        super(context);
    }

    public MyLinearLayour(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayour(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyLinearLayour(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("touchevent:", "MyLinearLayour onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
        //return true之后，touchevent事件不会传递到其子界面
        //return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("touchevent:", "MyLinearLayour dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("touchevent:", "MyLinearLayour onTouchEvent");
        return super.onTouchEvent(event);
    }
}
