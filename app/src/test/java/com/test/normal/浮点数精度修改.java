package com.test.normal;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by jix on 2019/1/25.
 */

public class 浮点数精度修改 {
    @Test
    public void 修改精度(){
        double value = 2.555D;
        BigDecimal b = new BigDecimal(value);
        double value_ = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(value_ + "");

        //去除科学计数法
        double stt = 999999999999.3344;
        DecimalFormat format = new DecimalFormat("###0.00");//不以科学计数法显示，并把结果用逗号隔开保留两位小数
        BigDecimal bigDecimal = new BigDecimal(stt);
        System.out.println(format.format(stt) + "");

        double d1 = 9.5;
        double d2 = 0.5;
        double d3 = d1 - d2;
        System.out.println(d3 + "");
        System.out.println(d3 == 9d);
        System.out.println((d3 - (int)d3) == 0d);
    }

}
