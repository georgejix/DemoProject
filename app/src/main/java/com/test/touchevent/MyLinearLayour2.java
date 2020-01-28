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

public class MyLinearLayour2 extends LinearLayout {
    public MyLinearLayour2(Context context) {
        super(context);
    }

    public MyLinearLayour2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayour2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyLinearLayour2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("touchevent:", "MyLinearLayour2 onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
        //return true之后，touchevent事件不会传递到其子界面
        //return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("touchevent:", "MyLinearLayour2 dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("touchevent:", "MyLinearLayour2 onTouchEvent");
        return super.onTouchEvent(event);
    }
}
