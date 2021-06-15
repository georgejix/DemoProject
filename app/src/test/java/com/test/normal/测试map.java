package com.test.normal;

import org.junit.Test;

import java.util.LinkedHashMap;

public class 测试map
{
    @Test
    public void test()
    {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put(null, null);
        map.put("1", "2");
        System.out.println(map.keySet().iterator().next());

    }
}
