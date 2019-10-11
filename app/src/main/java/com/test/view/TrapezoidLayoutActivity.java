package com.test.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.BaseActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.mplanet.testhandler.R;

@ContentView(R.layout.activity_trapezoid_layout)
public class TrapezoidLayoutActivity extends BaseActivity {
    private final String TAG = "TrapezoidLayoutActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //map.put("" + System.currentTimeMillis(), this);
    }

    //private static HashMap<String, Activity> map = new HashMap<String, Activity>();
}
