package com.test.touchevent;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;


/**
 * dispatchTouchEvent onInterceptTouchEvent onTouchEvent
 * 三者关系伪代码:
 *  public boolean dispatchTouchEvent(MotionEvent ev){
 *      boolean consume = false;
 *      if(onInterceptTouchEvent(ev)){
 *          consume = onTouchEvent(ev);
 *      }else{
 *          consume = child.dispatchTouchEvent(ev);
 *      }
 *      return consume;
 *  }
 *  分发，拦截，响应，返回值表示是否消耗当前事件，消耗则不再向下传递，自上而下传递，自下而上消耗
 */
@ContentView(R.layout.activity_touch_event)
public class TouchEventActivity extends Activity {
    private final String TAG = "TouchEventActivity";

    @ViewInject(R.id.textview_test1)
    private TextView test1TextView;

    private GestureDetectorCompat mDetector;
    private View pop;
    private PopupWindow popupWindow;
    private PopupWindowListener popClickListener;
    private ViewHolder viewHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        test1TextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(null != mDetector) {
                    mDetector.onTouchEvent(event);
                }
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        ViewConfiguration vc = ViewConfiguration.get(v.getContext());
                        int mSlop = vc.getScaledTouchSlop();
                        Log.d(TAG, "down " + mSlop);
                        //如果action_down返回false,OnTouchListener则将不再接收到后续事件，而由activity.onTouchEvent接收
                        //如果某一事件返回true,则activity.onTouchEvent不会接收到该事件
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG, "move");
                        return true;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "up");
                        return true;
                }
                return false;
            }
        });
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        //扩大view的touchevent响应区域
        test1TextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                test1TextView.getHitRect(rect);
                rect.right += 300;
                rect.left -=300;
                rect.top -= 300;
                rect.bottom += 300;
                TouchDelegate touchDelegate = new TouchDelegate(rect, test1TextView);
                ((View)test1TextView.getParent()).setTouchDelegate(touchDelegate);
            }
        });
    }

    class MyGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(TAG,"onDown: " + event.toString());
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d(TAG, "onLongPress: " + e.toString());
            showPop();
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Log.d(TAG, "onFling: " + event1.toString()+event2.toString());
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "activity down");
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "activity move");
                return true;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "activity up");
                return true;
        }
        return super.onTouchEvent(event);
    }






    private void showPop(){
        if(null == pop){
            pop = LayoutInflater.from(TouchEventActivity.this).inflate(R.layout.pop_touchevent, null);
            if(null == popClickListener){
                popClickListener = new PopupWindowListener();
            }
            if(null == viewHolder){
                viewHolder = new ViewHolder();
            }
            if(null != viewHolder){
                viewHolder.img = (ImageView) pop.findViewById(R.id.img);
            }
            if(null != popClickListener) {
                pop.findViewById(R.id.img).setOnClickListener(popClickListener);
            }
        }
        if(null == popupWindow && null != pop) {
            popupWindow = new PopupWindow(pop, (int)(getWindowWidth() - dp2px(100)), RelativeLayout.LayoutParams.WRAP_CONTENT);
            // 设置PopupWindow的背景
            //popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // 设置PopupWindow是否能响应外部点击事件
            popupWindow.setOutsideTouchable(false);
            // 设置PopupWindow是否能响应点击事件
            popupWindow.setTouchable(true);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setBackgroundAlpha(1f);
                }
            });
            popupWindow.setFocusable(true);
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
        if(null != popupWindow && !popupWindow.isShowing()){
            setBackgroundAlpha(0.7f);
            if (!isFinishing() && !isDestroyed()) {
                popupWindow.showAtLocation(test1TextView, Gravity.CENTER, 0, 0);
            }
            //popupWindow.showAsDropDown(titleBarView.getRight1View(), 0, 0);
        }
    }



    class ViewHolder{
        private ImageView img;
    }

    class PopupWindowListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
            }
        }
    }

    private void dimissPop(){
        if(!isFinishing() && !isDestroyed()) {
            if (null != popupWindow && popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
    }

    private void setBackgroundAlpha(float bgAlpha){
        if(!isFinishing() && !isDestroyed()) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = bgAlpha;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(layoutParams);
        }
    }

    //关闭popupWindow.setOutsideTouchable，重写此方法，否则pop显示情况下点击+，pop不会消失
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("touchevent:", "activity dispatchTouchEvent");
        if(null != ev && ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (!isFinishing() && !isDestroyed()) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return true;
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if(!isFinishing() && !isDestroyed()) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return true;
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    public float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    /**
     * @return 窗口宽度
     */
    public int getWindowWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * @return 窗口高度
     */
    public int getWindowHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
}
