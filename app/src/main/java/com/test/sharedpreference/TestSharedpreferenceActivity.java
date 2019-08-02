package com.test.sharedpreference;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.BaseActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.mplanet.testhandler.R;

@ContentView(R.layout.activity_test_sharedpreference)
public class TestSharedpreferenceActivity extends BaseActivity {
    private final static String TAG = "TestSharedpreferenceActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TestSharedPreference testSharedPreference = new TestSharedPreference(this);
        testSharedPreference.saveSomeSP();
        String sp = testSharedPreference.generateSPJsonString();
        Log.d(TAG, sp);
    }
}
