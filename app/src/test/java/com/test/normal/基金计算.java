package com.test.normal;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class 基金计算
{
    @Test
    public void test()
    {
        List<JJ> jjs = new ArrayList<>();
        jjs.add(new JJ(4420.77, -0.44, "nongye"));
        jjs.add(new JJ(3353.78, 3.77, "youse"));
        jjs.add(new JJ(1587.19, -0.09, "yiyao"));
        jjs.add(new JJ(3143.18, -0.38, "shengwu"));

        jjs.add(new JJ(3493.37, 1.95, "gexinnengyuan"));
        jjs.add(new JJ(4961.50, 1.13, "fuguoxinnengyuan"));
        jjs.add(new JJ(9937.86, 1.67, "meitan"));
        jjs.add(new JJ(7787.28, -0.61, "baijiu"));
        double total = 0;
        for (JJ jj : jjs)
        {
            total += jj.total * jj.percent / 100;
        }
        System.out.println(total);
    }

    class JJ
    {
        double total, percent;

        JJ(double total, double percent, String name)
        {
            this.total = total;
            this.percent = percent;
        }
    }
}
