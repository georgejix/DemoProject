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
import android.util.Log;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ContentView(R.layout.activity_test_some_view)
public class TestSomeViewActivity extends Activity {
    private final String TAG = "TestSomeViewActivity";

    @ViewInject(R.id.textview_one)
    private TextView oneTextView;
    @ViewInject(R.id.textview_two)
    private TextView twoTextView;
    @ViewInject(R.id.textview_three)
    private MTextView threeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        initView();
        Log.d(TAG, "oneTextView.width=" + oneTextView.getWidth());
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
        //threeTextView.setText(stringFilter(ToDBC(getResources().getString(R.string.alarm_service_contract))));
        //threeTextView.setText(ToSBC(getResources().getString(R.string.alarm_service_contract)));
        threeTextView.setMText(getResources().getString(R.string.alarm_service_contract));
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

    private String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    private String ToSBC(String input)
    {
        //半角转全角：
        char[] c=input.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (c[i]==32)
            {
                c[i]=(char)12288;
                continue;
            }
            if (c[i]<127)
                c[i]=(char)(c[i]+65248);
        }
        return new String(c);
    }

    private String stringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    private String stringFilter2(String str) {
        str = str.replaceAll("[", "【").replaceAll("]", "】")
                .replaceAll("!", "！").replaceAll(":", "：");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
