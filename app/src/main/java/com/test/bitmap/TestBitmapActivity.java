package com.test.bitmap;

import android.app.Activity;
import android.os.Environment;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;

import java.io.File;

@ContentView(R.layout.activity_test_bitmap)
public class TestBitmapActivity extends Activity {
    private final String TAG = "TestBitmapActivity";

    @ViewInject(R.id.listview)
    private ListView listView;

    private Adapter4TestBitmap adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ViewUtils.inject(this);
        if(null == adapter){
            if(Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                adapter = new Adapter4TestBitmap(this, new File(Environment.getExternalStorageDirectory() + File.separator + "pic"));
            }

            listView.setAdapter(adapter);
        }
    }
}
