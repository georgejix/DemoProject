package com.test.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.BuildConfig;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.App;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@ContentView(R.layout.activity_cut_pic)
public class CutPicActivity extends Activity {
    private final String TAG = "CutPicActivity";

    @ViewInject(R.id.img_1)
    private ImageView img1;

    private File cameraFile;

    private final int CHOOSE_FROM_ALBUM = 1001;
    private final int TAKE_PICTURE = 1002;
    private final int REQUEST_CROP_PHOTO = 1003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
    }

    public void chooseFromAlbum(View view){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);// 打开图库获取图片
        intent.setAction(Intent.ACTION_PICK);// 打开图库获取图片
        intent.setType("image/*");// 这个参数是确定要选择的内容为图片
        intent.putExtra("return-data", true);// 是否要返回，如果设置false取到的值就是空值
        startActivityForResult(intent, CHOOSE_FROM_ALBUM);
    }

    public void choosefromcamera(View view){
        cameraFile = new File(App.getFile(),"abc.jpg");
        if (cameraFile.exists()) {
            cameraFile.delete();
        }
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(24 <= Build.VERSION.SDK_INT){
            //与androidmenifest.xml中provider对应
            Uri photoUri = FileProvider.getUriForFile(
                    this,
                    com.mplanet.testhandler.BuildConfig.APPLICATION_ID + ".provider",
                    cameraFile);
            intent2.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        }else {
            intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
        }
        startActivityForResult(intent2, TAKE_PICTURE);
    }

    private Bitmap bitmap;
    private void afterAlbum(Uri uri){
        ContentResolver contentResolver = this.getContentResolver();
        try {
            bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
            img1.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CHOOSE_FROM_ALBUM:
                if(-1 == resultCode) {
                    afterAlbum(data.getData());
                    //cropPhoto(data.getData());
                }
                break;
            case TAKE_PICTURE:
                if(-1 == resultCode) {
                    if (null != cameraFile) {
                        bitmap = BitmapFactory.decodeFile(cameraFile.getAbsolutePath());
                        if (null != bitmap) {
                            img1.setImageBitmap(bitmap);
                        }
                        //cropPhoto(getUri(cameraFile));
                    }
                }
                break;
        }
    }

    /**
     * 裁剪图片
     * @param uri 需要 裁剪图像的Uri
     */ public void cropPhoto(Uri uri) {
         Intent intent = new Intent("com.android.camera.action.CROP");
         if (null != uri) {
             //7.0 安全机制下不允许保存裁剪后的图片
             // 所以仅仅将File Uri传入MediaStore.EXTRA_OUTPUT来保存裁剪后的图像
             //outUri

             intent.setDataAndType(uri, "image/*");
             // crop为true是设置在开启的intent中设置显示的view可以剪裁
             intent.putExtra("crop", true);
             // aspectX,aspectY 是宽高的比例，这里设置正方形
             intent.putExtra("aspectX", 1);
             intent.putExtra("aspectY", 1);
             //设置要裁剪的宽高
             intent.putExtra("outputX", 300);
             intent.putExtra("outputY", 300);
             //如果图片过大，会导致oom，这里设置为false
             intent.putExtra("return-data", false);
             //压缩图片
             intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
             //intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
             intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
             startActivityForResult(intent, REQUEST_CROP_PHOTO);
         }
     }

     private Uri getUri(File f){
         Uri uri = null;
         if(null != f) {
             if (Build.VERSION.SDK_INT >= 24) {
                 uri = FileProvider.getUriForFile(this, com.mplanet.testhandler.BuildConfig.APPLICATION_ID
                                 + ".provider", f);
             } else {
                 uri = Uri.fromFile(f);
             }
         }
         //grantUriPermission(com.mplanet.testhandler.BuildConfig.APPLICATION_ID, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
         return uri;
     }

    /**
     * 以时间戳命名将bitmap写入文件
     *
     * @param bitmap
     */
    public void writeFileByBitmap2(Bitmap bitmap) {
        String path = App.getFile();//手机设置的存储位置
        File file = new File(path);
        File imageFile = new File(file, System.currentTimeMillis() + ".png");


        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            imageFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
