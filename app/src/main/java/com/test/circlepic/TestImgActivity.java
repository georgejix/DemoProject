package com.test.circlepic;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;

@ContentView(R.layout.activity_test_img)
public class TestImgActivity extends Activity {
    private final String TAG = "TestImgActivity";

    @ViewInject(R.id.img01)
    private ImageView img01;
    @ViewInject(R.id.img02)
    private RoundImageView img02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),
                R.mipmap.pic2, options);
        options.inSampleSize = Math.max(options.outWidth / img01.getLayoutParams().width, options.outHeight / img01.getLayoutParams().height);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.pic2, options);
        Log.d(TAG, img01.getLayoutParams().width + "");
        img01.setImageDrawable(new CircleImageDrawable(bitmap));

    }
}
