package com.test.bitmap;

import android.graphics.Bitmap;

import com.App;
import com.AppManager;
import com.jakewharton.disklrucache.DiskLruCache;
import com.test.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2018/11/29.
 */

public class BitmapCacheDisk {
    private final Object mDiskCacheLock = new Object();
    private boolean mDiskCacheStarting = true;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private static final String DISK_CACHE_SUBDIR = "thumbnails";
    private static BitmapCacheDisk bitmapCacheDisk;
    private String dirName = "disklrucache";
    private String dirPath;
    private DiskLruCache diskLruCache;

    public static BitmapCacheDisk getInstance(){
        if(null == bitmapCacheDisk) {
            synchronized (BitmapCacheDisk.class) {
                if(null == bitmapCacheDisk){
                    bitmapCacheDisk = new BitmapCacheDisk();
                }
            }
        }
        return bitmapCacheDisk;
    }

    private BitmapCacheDisk(){
        dirPath = App.getFile() + File.separator + dirName + File.separator;
        File dirFile = new File(dirPath);
        try {
            diskLruCache = DiskLruCache.open(dirFile, Utils.getAppVersion(AppManager.getAppManager().currentActivity()), 1, DISK_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBitmapToDiskCache(String key, Bitmap bitmap) {
        put(key, Utils.bitmap2Bytes(bitmap));
    }

    public Bitmap getBitmapFromDiskCache(String key) {
        byte[] bytes = (byte[]) get(key);
        if (bytes == null) return null;
        return Utils.bytes2Bitmap(bytes);
    }

    public void put(String key, Object value){
        try {
            key = Utils.toMd5Key(key);
            DiskLruCache.Editor editor = diskLruCache.edit(key);
            if (editor != null) {
                OutputStream os = editor.newOutputStream(0);
                if (Utils.writeObject(os, value)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
                diskLruCache.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据key获取保存对象
     * @param key
     * @param <T>
     * @return
     */
    public <T> T get(String key){
        try {
            key = Utils.toMd5Key(key);
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);

            if (snapshot != null) {
                InputStream inputStream = snapshot.getInputStream(0);
                return (T)Utils.readObject(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
