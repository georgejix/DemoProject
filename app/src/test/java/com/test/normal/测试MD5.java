package com.test.normal;

import org.junit.Test;

import java.security.MessageDigest;
import java.util.Random;

public class 测试MD5
{
    @Test
    public void test(){
        try {
            String str = "12324";
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(str.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Random random = new Random();
        System.out.println(random.nextInt(10000) + "");
    }
}
