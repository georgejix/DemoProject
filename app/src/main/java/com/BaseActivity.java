package com;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lidroid.xutils.ViewUtils;

/**
 * Created by Administrator on 2019/2/21.
 */

public class BaseActivity extends Activity {
    private final String TAG = "BaseActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager appManager = AppManager.getAppManager();
        appManager.addActivity(this);
        ViewUtils.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager appManager = AppManager.getAppManager();
        appManager.removeActivity(this);
        Log.d(TAG, getClass().getSimpleName() + " destory");
    }
}
