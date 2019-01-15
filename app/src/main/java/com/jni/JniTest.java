package com.jni;

/**
 * Created by jix on 2019/1/14.
 */

public class JniTest {
    static{
        System.loadLibrary("jniTest");
    }

    public native int test();
}
