package com.test.singleinstance;

import org.junit.Test;

public class 测试String
{
    @Test
    public void test(){
        System.out.println("2694321055863".substring(7));
        System.out.println(Double.parseDouble("2694321055863".substring(7)) / 1000);
        String birth = "2012-11-23T22:00:22";
        System.out.println(birth.substring(0, birth.indexOf("T")));
        double d1 = 10.0;
        int i1 = 10;
        System.out.println(d1 == i1);
        String str = "123";
        String s[] = str.split("\n");
        System.out.println(str + "," + s.length);
        System.out.println("我".getBytes().length);
        System.out.println("我 1".substring(0, "我 1".indexOf(" ")));


    }
}
