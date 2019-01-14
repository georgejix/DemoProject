package com.test.singleinstance;

/**
 * Created by Administrator on 2018/12/28.
 */

public class TestExtendsA {
    private final String TAG = "TestExtends";

    public String label = "A";
    public void show(){
        System.out.println(TAG + "A");
    }
    TestExtendsA(){
        System.out.println("构造函数A");
    }
}
