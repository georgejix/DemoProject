package com.test.jpeg;

import android.graphics.YuvImage;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.BaseActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.mplanet.testhandler.R;

@ContentView(R.layout.activity_jpeg_yuv_rgb)
public class JpegYuvRgbActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //jpeg转rgb

    //rgb转jpeg

    //yuv转jpeg
    private void yuvToJpeg() {
        /*try {
            YuvImage yuvimage = new YuvImage(data, ImageFormat.NV21, width, height, null);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            yuvimage.compressToJpeg(new Rect(0, 0,width, height), 80, baos);

            Bitmap bmp = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);

            outStream = new FileOutputStream(
                    String.format("/mnt/sdcard/Camera/%d_%s_%s.jpg",
                            System.currentTimeMillis(),String.valueOf(width),String.valueOf(height)));
            bmp.compress(Bitmap.CompressFormat.JPEG, 85, outStream);
            outStream.write(baos.toByteArray());
            outStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }*/
    }

}
