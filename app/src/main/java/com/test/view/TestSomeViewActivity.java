package com.test.view;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
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
    }
}
