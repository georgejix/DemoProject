package com.test.singleinstance;

/**
 * Created by Administrator on 2018/12/28.
 */

public class 测试继承A {
    private final String TAG = "测试继承";

    public String label = "两个接口函数重名的实现";
    public void show(){
        System.out.println(TAG + "两个接口函数重名的实现");
    }
    测试继承A(){
        System.out.println("构造函数A");
    }
}
