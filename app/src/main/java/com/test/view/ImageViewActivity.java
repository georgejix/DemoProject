package com.test.view;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;

@ContentView(R.layout.activity_image_view)
public class ImageViewActivity extends Activity {

    @ViewInject(R.id.img_test)
    private ImageView testImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        testImg.setImageResource(R.mipmap.pic4);
    }
}
