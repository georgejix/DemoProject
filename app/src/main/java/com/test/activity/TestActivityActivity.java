package com.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mplanet.testhandler.R;
import com.test.handler.TestHandlerActivity;

public class TestActivityActivity extends Activity {
    private final String TAG = "TestActivity";

    private ScreenOrientationUtil screenOrientation;
    private TextView popTextView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_test_activity);
        findViewById(R.id.textview_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestActivityActivity.this, TestHandlerActivity.class));
                overridePendingTransition(R.anim.up_in, R.anim.up_out);
            }
        });
        popTextView = (TextView) findViewById(R.id.textview_test_pop);
        popTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop();
            }
        });
        screenOrientation = ScreenOrientationUtil.getInstance();
        screenOrientation.start(this);
        editText = (EditText) findViewById(R.id.edittext_t1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        //android.os.Debug.stopMethodTracing();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        if(null != editText && null != editText.getText()){
            outState.putString("edit", editText.getText().toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
        if(null != editText) {
            editText.setText(savedInstanceState.getString("edit", ""));
            editText.setSelection(editText.getText().toString().length());
        }
    }

    private View editPop;
    private PopupWindow editPopupWindow;
    private PopupWindowListener popClickListener;
    private ViewHolder viewHolder;

    private void showPop(){
        if(null == editPop){
            editPop = LayoutInflater.from(TestActivityActivity.this).inflate(R.layout.pop_test, null);
            if(null == popClickListener){
                popClickListener = new PopupWindowListener();
            }
            if(null != viewHolder){
            }
        }
        if(null == editPopupWindow) {
            editPopupWindow = new PopupWindow(editPop, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            // 设置PopupWindow的背景
            //popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // 设置PopupWindow是否能响应外部点击事件
            editPopupWindow.setOutsideTouchable(false);
            // 设置PopupWindow是否能响应点击事件
            editPopupWindow.setTouchable(true);
            editPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setBackgroundAlpha(1f);
                }
            });
            editPopupWindow.setFocusable(true);
            editPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
        if(!editPopupWindow.isShowing()){
            setBackgroundAlpha(0.7f);
            editPopupWindow.showAtLocation(popTextView, Gravity.CENTER, 0, 0);
            //popupWindow.showAsDropDown(titleBarView.getRight1View(), 0, 0);
        }
    }



    class ViewHolder{
    }

    class PopupWindowListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
            }
        }
    }

    private void dimissMorePop(){
        if(null != editPopupWindow && editPopupWindow.isShowing()){
            editPopupWindow.dismiss();
        }
    }

    private void setBackgroundAlpha(float bgAlpha){
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(layoutParams);
    }

    //关闭popupWindow.setOutsideTouchable，重写此方法，否则pop显示情况下点击+，pop不会消失
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (editPopupWindow != null && editPopupWindow.isShowing()) {
            editPopupWindow.dismiss();
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (editPopupWindow != null && editPopupWindow.isShowing()) {
                editPopupWindow.dismiss();
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
