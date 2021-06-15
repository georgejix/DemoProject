package com.test.算法;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class _2_数组中寻找两数之和等于给定值
{
    @Test
    public void test()
    {
        int[] a = new int[]{1, 2, 3, 4, 5, 6};
        数组中寻找两数之和等于给定值(a, 6);
    }

    private void 数组中寻找两数之和等于给定值(int a[], int b)
    {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < a.length; i++)
        {
            int rest = b - a[i];
            if (map.containsKey(rest))
            {
                System.out.println(map.get(rest) + "," + i);
                return;
            }
            else
            {
                map.put(a[i], i);
            }
        }
    }

}
