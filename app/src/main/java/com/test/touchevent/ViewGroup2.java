package com.test.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ViewGroup2 extends LinearLayout {
    private final String TAG = "touchevent";

    public ViewGroup2(Context context) {
        super(context);
    }

    public ViewGroup2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "viewgroup2.dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "viewgroup2.onTouchEvent");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "viewgroup2 down");
                return false;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "viewgroup2 move");
                return true;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "viewgroup2 up");
                return false;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "viewgroup2.onInterceptTouchEvent");
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onInterceptTouchEvent viewgroup2 down");
                return false;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onInterceptTouchEvent viewgroup2 move");
                return true;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onInterceptTouchEvent viewgroup2 up");
                return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

}
