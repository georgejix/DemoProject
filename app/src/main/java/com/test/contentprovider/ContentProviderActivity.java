package com.test.contentprovider;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.BaseActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;

import java.util.Random;

@ContentView(R.layout.activity_content_provider)
public class ContentProviderActivity extends BaseActivity {
    private final String TAG = "ContentProviderActivity";

    @ViewInject(R.id.textview_content)
    private TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void add(View view){
        Uri uri1 = Uri.parse("content://com.mplanet.testhandler/students/insert");
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", (int)(Math.random() * 1000) + "");
        contentValues.put("name", "11");
        contentValues.put("tel_no", "11");
        contentValues.put("cls_id", "11");
        //获得ContentResolver对象，调用方法
        getContentResolver().insert(uri1,contentValues);
    }
    public void delete(View view){
    }
    public void alter(View view){
    }
    public void query(View view){
    }
}
