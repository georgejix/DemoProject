package com.test.singleinstance;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Administrator on 2019/2/13.
 */

public class 测试日期 {

    @Test
    public void test(){
        //t1();
    }

    //日期月份+1
    private void t1(){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(2019, 0, 31);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month + 2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy--MM--dd");
        System.out.println(sdf.format(calendar.getTime()));
    }
}
