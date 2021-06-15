package com.test.normal;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class 测试enum
{

    @Test
    public void t1(){
        System.out.println(Color.Black.name());
        System.out.println(Color.Black.toString());
        String[] a = Color.getNames();
        System.out.println(Color.getNames());

    }

    enum Color
    {
        Red("红色"),
        Blue("蓝色"),
        Black(null);

        String mColorName;

        Color(String colorName)
        {
            mColorName = colorName;
        }

        public static String[] getNames(){
            List<String> nameList = new ArrayList<>();
            for(Color c : Color.values()){
                nameList.add(c.mColorName);
            }
            return nameList.toArray(new String[0]);
        }

    }
}
