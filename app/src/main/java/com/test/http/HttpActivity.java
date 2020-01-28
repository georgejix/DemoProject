package com.test.http;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;
import com.test.util.HttpURLConnectionUtil;

@ContentView(R.layout.activity_http)
public class HttpActivity extends Activity {
    private final String TAG = "HttpActivity";

    @ViewInject(R.id.img_1)
    private ImageView img1;

    private ViewHandler viewHandler;
    private Bitmap bitmap;

    private final int REFRESH_IMG = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        if(null == viewHandler){
            viewHandler = new ViewHandler();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //String s = HttpUtils.getInstance().sendGetMessage("http://pic1.cxtuku.com/00/06/78/b9903ad9ea2b.jpg", "");
                byte b[] = HttpURLConnectionUtil.getInstance().sendGet("http://pic1.cxtuku.com/00/06/78/b9903ad9ea2b.jpg");
                if(null != b) {
                    bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                }
                if(null != viewHandler){
                    viewHandler.sendEmptyMessage(REFRESH_IMG);
                }

                /*Map<String, String> params = new HashMap<String, String>();
                        params.put("username", "getOrderRecord");
                        params.put("password", "122223");
                String s = HttpURLConnectionUtil.getInstance().sendPostMessage("https://passport.baidu.com/v2/api/?login", params, "utf-8");
                if(null != s){
                    Log.d(TAG, s);
                }*/
            }
        }).start();

    }

    class ViewHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case REFRESH_IMG:
                    if(null != img1 && null != bitmap) {
                        img1.setImageBitmap(bitmap);
                    }
                    break;
            }
        }
    }
}
