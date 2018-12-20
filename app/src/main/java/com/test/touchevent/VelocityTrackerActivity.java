package com.test.touchevent;

import android.app.Activity;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;

@ContentView(R.layout.activity_velocity_tracker)
public class VelocityTrackerActivity extends Activity {
    private final String TAG = "VelocityTracker";

    private VelocityTracker mVelocityTracker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
    }

    int index;
    int pointerId;
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        //MotionEventCompat.getActionMasked(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                index = event.getActionIndex();
                pointerId = event.getPointerId(index);

                if(null == mVelocityTracker){
                    mVelocityTracker = VelocityTracker.obtain();
                }else{
                    mVelocityTracker.clear();
                }
                mVelocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                mVelocityTracker.computeCurrentVelocity(1000);
                //可以根据index或者pointid获取对应点击事件（主事件、副事件）的数值
                Log.d(TAG, "xx:" + VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId));
                Log.d(TAG, "vy:" + mVelocityTracker.getYVelocity());
                if(-1 != event.findPointerIndex(pointerId)) {
                    Log.d(TAG, "x=" + event.getX(event.findPointerIndex(pointerId)));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(event);
    }
}
