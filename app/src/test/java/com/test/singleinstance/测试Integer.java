package com.test.singleinstance;

import org.junit.Test;

public class 测试Integer
{
    @Test
    public void test(){
        Integer i1 = 1;
        Integer i2 = null;
        int i3 = 1;
        System.out.println(i1 == i2);
        System.out.println(i1 == i3);
        //System.out.println(i2 == i3);
    }
}
