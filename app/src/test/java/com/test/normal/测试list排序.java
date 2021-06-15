package com.test.normal;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class 测试list排序
{
    @Test
    public void test()
    {
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("abb");
        list.add("bcd");
        list.add("12a");
        list.add("23b");
        Collections.sort(list, new PinYinComparator());
        for (String str : list)
        {
            System.out.println(str);
        }
    }

    class PinYinComparator implements Comparator
    {
        @Override
        public int compare(Object o1, Object o2)
        {
            String str1 = (String) o1;
            String str2 = (String) o2;
            return str1.compareTo(str2);
        }

    }
}
