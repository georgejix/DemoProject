package com.test.算法;

import org.junit.Test;

public class _1_a加b
{
    @Test
    public void test()
    {
        System.out.println(sum(-500, 656));
    }

    private int sum(int a, int b)
    {
        if (0 == a)
        {
            return b;
        }
        if (0 == b)
        {
            return a;
        }
        int sum = a ^ b;
        int array = (a & b) << 1;
        System.out.println("sum=" + sum + ",array=" + array);
        return sum(sum, array);
    }
}
