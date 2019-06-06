package com.test.touchevent;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class View1 extends View {
    private final String TAG = "touchevent";

    public View1(Context context) {
        super(context);
    }

    public View1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "view1.dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "view1.onTouchEvent");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "view down");
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "view move");
                return true;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "view up");
                return true;
        }
        return super.onTouchEvent(event);
    }


}
