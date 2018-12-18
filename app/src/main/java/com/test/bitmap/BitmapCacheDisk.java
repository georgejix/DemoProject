package com.test.bitmap;

/**
 * Created by Administrator on 2018/11/29.
 */

public class BitmapCacheDisk {
    private final Object mDiskCacheLock = new Object();
    private boolean mDiskCacheStarting = true;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private static final String DISK_CACHE_SUBDIR = "thumbnails";
    private static BitmapCacheDisk bitmapCacheDisk;

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
}
