package com.test.bitmap;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by jix on 2018/11/29.
 */

public class BitmapCache {
    private LruCache<String, Bitmap> mMemoryCache;
    private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    final int cacheSize = maxMemory / 8;
    private static BitmapCache bitmapCache;

    public static BitmapCache getInstance(){
        if(null == bitmapCache) {
            synchronized (BitmapCache.class) {
                if(null == bitmapCache){
                    bitmapCache = new BitmapCache();
                }
            }
        }
        return bitmapCache;
    }

    public BitmapCache(){
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

}
