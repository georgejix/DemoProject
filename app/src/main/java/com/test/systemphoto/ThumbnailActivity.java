package com.test.systemphoto;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.mplanet.testhandler.R;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_thumbnail)
public class ThumbnailActivity extends Activity {
    private final String TAG = "ThumbnailActivity";

    private ContentResolver cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        checkPermission();
        if(PermissionChecker.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            new ImageAsyncTask().execute();
        }
    }

    private class ImageAsyncTask extends AsyncTask<Void, Void, Object> {
        @Override
        protected Object doInBackground(Void... params) {
            cr = getContentResolver();
            // 获取缩略图
            String[] projection = { BaseColumns._ID, MediaStore.Images.Thumbnails.IMAGE_ID,
                    MediaStore.Images.Thumbnails.DATA };
            Cursor cur = cr.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection,
                    null, null, null);

            if (cur != null && cur.moveToFirst()) {
                int image_id;
                String image_path;
                int image_idColumn = cur.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID);
                int dataColumn = cur.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
                do {
                    image_id = cur.getInt(image_idColumn);
                    image_path = cur.getString(dataColumn);
                    Log.d(TAG, image_id + "file://" + image_path);
                } while (cur.moveToNext());
            }
            // 获取原图
            Cursor cursor = cr.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null,
                    null, "date_modified DESC");
            String _path = "_data";
            String _album = "bucket_display_name";
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int index = 0;
                    int _id = cursor.getInt(cursor.getColumnIndex("_id"));
                    String path = cursor
                            .getString(cursor.getColumnIndex(_path));
                    String album = cursor.getString(cursor
                            .getColumnIndex(_album));
                    Log.d(TAG, _id + "," + path + "," + album);
                } while (cursor.moveToNext());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);

        }
    }

    private void checkPermission(){
        List<String> permissionLists = new ArrayList<>();
        if (Build.VERSION.SDK_INT>=23&& ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionLists.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(!permissionLists.isEmpty()){//说明肯定有拒绝的权限
            ActivityCompat.requestPermissions(this, permissionLists.toArray(new String[permissionLists.size()]), 0);
        }
    }
}
