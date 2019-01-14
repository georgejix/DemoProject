package com.test.singleinstance;

/**
 * Created by Administrator on 2018/12/28.
 */

public class TestExtendsB extends TestExtendsA{
    private final String TAG = "TestExtends";

    public String label = "B";
    public void show(){
        System.out.println(TAG + "B");
    }
    TestExtendsB(){
        System.out.println("构造函数B");
    }
}
