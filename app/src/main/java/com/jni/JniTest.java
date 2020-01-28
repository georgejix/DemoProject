package com.jni;

import android.util.Log;

/**
 * Created by jix on 2019/1/14.
 */

public class JniTest {
    private static final String TAG = "JniTest";

    static{
        System.loadLibrary("jniTest");
    }

    public native int test();

    public void onProgressCallBack(long i, long m){
        Log.d(TAG, i + "," + m);
    }

    public void myCallback(int a) {
        Log.e(TAG, "Callback: " + a);
    }
}
