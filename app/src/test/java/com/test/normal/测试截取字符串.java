package com.test.normal;

import org.junit.Test;

public class 测试截取字符串
{
    @Test
    public void main(){
        String s1 = "金银铜卡789";
        System.out.println(s1.length() + "," + s1.substring(0, 5));
        if(GoodsType.getType("3").equals(GoodsType.WEIGHT)){
            System.out.println("111");
        }
    }
}
