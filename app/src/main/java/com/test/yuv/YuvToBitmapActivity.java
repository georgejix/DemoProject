package com.test.yuv;

import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import com.App;
import com.BaseActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@ContentView(R.layout.activity_yuv_to_bitmap)
public class YuvToBitmapActivity extends BaseActivity {
    private final static String TAG = "YuvToBitmapActivity";

    private String yuvPath = "";
    private final int WIDTH = 1280, HEIGHT = 720;

    @ViewInject(R.id.img_bitmap)
    private ImageView bitmapImg;

    static {
        System.loadLibrary("opencv_java");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        yuvPath = App.getFile() + File.separator + "yuv1.yuv";
        transform();
        saveYuvToPic();
        saveYuvToPic2();
    }

    private void transform(){
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(yuvPath));
            byte in[] = new byte[fileInputStream.available()];
            fileInputStream.read(in);
            transNv12ToNv21(in);
            NV21ToBitmap nv21ToBitmap = new NV21ToBitmap(this, WIDTH, HEIGHT);
            Bitmap bitmap = nv21ToBitmap.nv21ToBitmap(in);
            bitmapImg.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transNv12ToNv21(byte in[]){
        if(null == in || in.length <= WIDTH * HEIGHT)
            return;
        byte temp;
        for(int index = WIDTH * HEIGHT; index + 1 < in.length; index+=2){
            temp = in[index];
            in[index] = in[index+1];
            in[index+1] = temp;
        }
    }


    private void saveYuvToPic(){
        Mat mat = new Mat(480, 640, CvType.CV_8UC1);
        byte bytes[] = new byte[480 * 640];
        mat.put(0, 0, bytes);
        Highgui.imwrite(App.getFile() + File.separator + "yuvtopic.jpg", mat);
    }

    private void saveYuvToPic2(){
        File file = new File(App.getFile() + File.separator + "yuvtopic2.jpg");
        try {
            file.createNewFile();
            FileOutputStream filecon = new FileOutputStream(file);
            byte bytes[] = new byte[640 * 480 * 3 / 2];
            YuvImage image = new YuvImage(bytes, ImageFormat.NV21, 640, 480, null);
            image.compressToJpeg(new Rect(0, 0, image.getWidth(), image.getHeight()), 80, filecon);
        }catch (Exception e){}
    }
}
