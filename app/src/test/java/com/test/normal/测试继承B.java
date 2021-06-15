package com.test.normal;

/**
 * Created by Administrator on 2018/12/28.
 */

public class 测试继承B extends 测试继承A
{
    private final String TAG = "测试继承";

    public String label = "B";

    public void show()
    {
        System.out.println(TAG + "B");
    }

    测试继承B()
    {
        System.out.println("构造函数B");
    }

    测试继承B(int i)
    {
        System.out.println("构造函数B" + i);
    }
}
