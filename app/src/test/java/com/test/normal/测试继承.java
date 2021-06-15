package com.test.normal;


import org.junit.Test;

/**
 * Created by jix on 2018/12/28.
 * 继承中，方法覆盖，变量隐藏
 */

public class 测试继承 {
    private final String TAG = "测试继承";
    @Test
    public void t(){
        测试继承A a1 = new 测试继承A();
        a1.show();
        System.out.println(a1.label);
        System.out.println("---");

        测试继承A a2 = new 测试继承B(2);
        a2.show();
        System.out.println(a2.label);
        System.out.println("---");

        测试继承B b1 = new 测试继承B();
        b1.show();
        System.out.println(b1.label);
        System.out.println("---");

    }
}
