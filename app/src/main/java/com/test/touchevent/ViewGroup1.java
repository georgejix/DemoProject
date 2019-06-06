package com.test.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ViewGroup1 extends LinearLayout {
    private final String TAG = "touchevent";

    public ViewGroup1(Context context) {
        super(context);
    }

    public ViewGroup1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "viewgroup1.dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "viewgroup1.onTouchEvent");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "viewgroup1 down");
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "viewgroup1 move");
                return true;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "viewgroup1 up");
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "viewgroup1.onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

}
