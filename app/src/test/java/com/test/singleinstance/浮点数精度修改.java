package com.test.singleinstance;

import org.junit.Test;

import java.math.BigDecimal;

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
    }

}
