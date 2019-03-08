package com.test.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;
import com.test.Utils;

@ContentView(R.layout.activity_test_some_view)
public class TestSomeViewActivity extends Activity {

    @ViewInject(R.id.textview_one)
    private TextView oneTextView;
    @ViewInject(R.id.textview_two)
    private TextView twoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        initView();
    }

    private void initView(){
        //设置不同颜色
        String textOne = oneTextView.getText().toString();
        SpannableStringBuilder sp = new SpannableStringBuilder(textOne);
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)),
                0,
                textOne.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)),
                3,
                11,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        oneTextView.setText(sp);

        //设置不同字体
        String textTwo = twoTextView.getText().toString();
        SpannableStringBuilder spTwo = new SpannableStringBuilder(textTwo);
        spTwo.setSpan(new AbsoluteSizeSpan(Utils.sp2px(this, 19)),
                3,
                11,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        twoTextView.setText(spTwo);
        addButton();
    }

    private WindowManager windowManager;
    private Button button;
    //通过windowmanager添加一个view到window，可以通过type设置z轴优先级（应用window级：1-99/子window级：1000-1999/系统window级：2000-2999）
    private void addButton(){
        //判断是否有 允许显示在其他应用的上层 的权限
        if (! Settings.canDrawOverlays(TestSomeViewActivity.this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent,10);
        }else {
            windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            button = new Button(this);
            button.setText("12345");
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0
                params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }
            params.format = PixelFormat.TRANSLUCENT;// 支持透明
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
            params.gravity = Gravity.TOP | Gravity.LEFT;
            params.x = 100;
            params.y = 300;
            params.width = 100;
            params.height = 100;
            windowManager.addView(button, params);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != button && null != windowManager){
            windowManager.removeView(button);
        }
    }
}
