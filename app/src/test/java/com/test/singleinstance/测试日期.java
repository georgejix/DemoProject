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
        //t2();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        System.out.println(hour + "," + minute + "," + second);
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

    //打印日期选择控件，所需数据
    private void t2(){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int minDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDayOfWeek = calendar.getFirstDayOfWeek();//1:sunday，7:Saturday 获取一周的第一天
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        print("minDay=" + minDay);
        print("maxDay=" + maxDay);
        print("firstDayOfWeek=" + firstDayOfWeek);
        print("dayOfWeek=" + dayOfWeek);
    }

    class Day{
        private int year;
        private int month;
        private int day;
        private int dayOfWeek;
    }

    private void print(String s){
        if(null != s)
        System.out.println(s);
    }
}
