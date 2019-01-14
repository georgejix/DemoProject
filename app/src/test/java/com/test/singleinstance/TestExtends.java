package com.test.singleinstance;


import android.util.Log;

import org.junit.Test;

/**
 * Created by Administrator on 2018/12/28.
 */

public class TestExtends {
    private final String TAG = "TestExtends";
    @Test
    public void t(){
        TestExtendsA a1 = new TestExtendsA();
        a1.show();
        System.out.println(a1.label);
        System.out.println("---");

        TestExtendsA a2 = new TestExtendsB();
        a2.show();
        System.out.println(a2.label);
        System.out.println("---");

        TestExtendsB b1 = new TestExtendsB();
        b1.show();
        System.out.println(b1.label);
        System.out.println("---");

    }
}
