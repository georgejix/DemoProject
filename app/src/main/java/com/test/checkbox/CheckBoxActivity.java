package com.test.checkbox;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;

@ContentView(R.layout.activity_check_box)
public class CheckBoxActivity extends Activity {

    @ViewInject(R.id.checkbox_one)
    private CheckBox checkBoxOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        initView();
    }

    private void initView(){
        //取得设置好的drawable对象
        Drawable drawable = this.getResources().getDrawable(R.drawable.selector_checkbox_one);

        //设置drawable对象的大小
        drawable.setBounds(0,0,40,40);

        //设置CheckBox对象的位置，对应为左、上、右、下
        checkBoxOne.setCompoundDrawables(drawable,null,null,null);
    }
}
