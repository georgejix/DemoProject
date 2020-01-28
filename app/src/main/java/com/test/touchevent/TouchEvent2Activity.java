package com.test.touchevent;

import android.os.Bundle;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;
import com.BaseActivity;

@ContentView(R.layout.activity_touch_event2)
public class TouchEvent2Activity extends BaseActivity {

    @ViewInject(R.id.view1)
    private View1 view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
