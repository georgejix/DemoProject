package com.test.normal;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class 测试Set
{
    @Test
    public void 测试Set()
    {
        Set<String> set = new HashSet<>();
        set.add("1");
        set.add("1");
        set.add("1");
        System.out.println(set.size());
    }
}
