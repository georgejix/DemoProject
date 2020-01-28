package com.test.yuv;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.App;
import com.BaseActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;

import java.io.File;
import java.io.IOException;

@ContentView(R.layout.activity_mp4_to_yuv)
public class Mp4ToYuvActivity extends BaseActivity implements Handler.Callback {
    private final static String TAG = "Mp4ToYuvActivity";

    private OnFrameListener onFrameListener;
    private DisplayMetrics dm;
    private boolean initImage = false;
    private NV21ToBitmap nv21ToBitmap;
    private int imgWidth, imgHeight;
    private Handler mHandler;

    private final static int SET_IMG = 1001;
    private final static int PARSE_MP4 = 1002;

    @ViewInject(R.id.img)
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        mHandler.sendEmptyMessage(PARSE_MP4);
    }

    private void init(){
        dm = getResources().getDisplayMetrics();
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper(), this);

        onFrameListener = new OnFrameListener() {
            @Override
            public void onFrame(final Image image) {
                if(!initImage){
                    imgWidth = dm.widthPixels;
                    imgHeight = dm.widthPixels * image.getHeight() / image.getWidth();
                    mHandler.sendEmptyMessage(SET_IMG);
                    nv21ToBitmap = new NV21ToBitmap(Mp4ToYuvActivity.this,
                            imgWidth, imgHeight, image.getWidth(), image.getHeight());
                    initImage = true;
                }

                byte[] yuv = new byte[image.getWidth() * image.getHeight() * 3 / 2];
                image.getPlanes()[0].getBuffer().get(yuv, 0, image.getWidth() * image.getHeight());
                image.getPlanes()[2].getBuffer().get(yuv, image.getWidth() * image.getHeight(),
                        image.getWidth() * image.getHeight() / 2 - 1);
                /*image.getPlanes()[2].getBuffer().get(yuv, image.getWidth() * image.getHeight() * 5 / 4,
                        image.getWidth() * image.getHeight() / 4);*/
                final Bitmap bitmap = nv21ToBitmap.nv21ToBitmap(yuv);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        img.setImageBitmap(bitmap);
                    }
                });
            }
        };
    }

    private void TransformMp4ToYuv(){
        String mp4Path = App.getFile() + File.separator + "1.mp4";
        Mp4Decoder mp4Decoder = new Mp4Decoder();
        try {
            mp4Decoder.init(mp4Path);
            mp4Decoder.setOnFrameListener(onFrameListener);
            mp4Decoder.videoDecode();
            mp4Decoder.release();
        } catch (IOException e) {
            Log.d(TAG, e + "");
        }

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case SET_IMG:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, imgWidth + "," + imgHeight);
                        img.setLayoutParams(new LinearLayout.LayoutParams(imgWidth,
                                imgHeight));
                    }
                });
                break;
            case PARSE_MP4:
                TransformMp4ToYuv();
                break;
        }
        return true;
    }
}
