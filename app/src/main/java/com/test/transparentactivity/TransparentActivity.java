package com.test.transparentactivity;

import android.os.Bundle;

import com.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.mplanet.testhandler.R;

@ContentView(R.layout.activity_transparent)
public class TransparentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
    }
}
