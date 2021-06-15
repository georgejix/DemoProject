package com.test.normal;

import org.junit.Test;

public class TestInnerClass {

    @Test
    public void test(){
        final String str = "abc";
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(str);
            }
        }).start();
    }
}

