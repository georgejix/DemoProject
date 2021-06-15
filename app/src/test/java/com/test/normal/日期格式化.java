package com.test.normal;

import android.annotation.SuppressLint;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 */

/**
 * @brief
 * @details
 * @author Administrator
 *
 */
public class 日期格式化
{

    @Test
    public void main()
    {
        // TODO Auto-generated method stub
        //日期格式化 t = new 日期格式化();
        //t.t1();
        //t.t2();
        //t.t3();
        //System.out.println(getSeconds("2019-01-09 01:05:53"));
        //System.out.println(dealDateFormat("2019-10-29T00:00:00.000Z"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(getTimesWeekMorning()));
        System.out.println(sdf.format(getTimesWeekNight()));
        System.out.println(sdf.format(getTimesMonthMorning()));
        System.out.println(sdf.format(getTimesMonthNight()));
        System.out.println(sdf.format(getCurrentQuarterStartTime()));
        System.out.println(sdf.format(getCurrentQuarterEndTime()));
        System.out.println(sdf.format(getHalfYearAgoTime()));
    }


    private long getSeconds(String time)
    {
        long seconds = 0;
        try
        {
            time = time.substring(time.indexOf(" ") + 1);
            String times[] = time.split(":");
            seconds = Long.parseLong(times[0]) * 60 * 60 + Long.parseLong(times[1]) * 60 + Long.parseLong(times[2]);
        } catch (Exception e)
        {
        }
        return seconds;
    }

    private void t1()
    {
        SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd");

        try
        {
            System.out.println(sdfOne.parse(sdfOne.format(System.currentTimeMillis())).getTime() + "");
        } catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void t2()
    {
        SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            long today = sdfOne.parse(sdfOne.format(System.currentTimeMillis())).getTime() / 1000;
            System.out.println(sdfOne.format(today * 1000) + "▼");
        } catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void t3()
    {
        SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfOne_ = new SimpleDateFormat("yyyy年MM月dd日");
        try
        {
            long today = sdfOne.parse("2018-08-23").getTime() / 1000;
            System.out.println(sdfOne_.format(today * 1000) + "▼");
        } catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String dealDateFormat(String oldDateStr)
    {
        try
        {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
            Date date = df.parse(oldDateStr);
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            return df2.format(date);
        } catch (Exception e)
        {

        }
        return "";
    }

    // 获得本周一0点时间
    public static long getTimesWeekMorning()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTimeInMillis();
    }

    // 获得本周日24点时间
    public static long getTimesWeekNight()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getTimesWeekMorning());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return cal.getTimeInMillis() - 1000;
    }

    // 获得本月第一天0点时间
    public static long getTimesMonthMorning()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTimeInMillis();
    }

    // 获得本月最后一天24点时间
    public static long getTimesMonthNight()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTimeInMillis() - 1000;
    }

    /**
     * 当前季度的开始时间
     */
    @SuppressLint("SimpleDateFormat")
    public static long getCurrentQuarterStartTime()
    {
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = null;
        try
        {
            if (currentMonth <= 3)
            {
                cal.set(Calendar.MONTH, 0);
            }
            else if (currentMonth <= 6)
            {
                cal.set(Calendar.MONTH, 3);
            }
            else if (currentMonth <= 9)
            {
                cal.set(Calendar.MONTH, 4);
            }
            else if (currentMonth <= 12)
            {
                cal.set(Calendar.MONTH, 9);
            }
            cal.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(cal.getTime()) + " 00:00:00");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return now.getTime();
    }

    /**
     * 当前季度的结束时间
     */
    public static long getCurrentQuarterEndTime()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getCurrentQuarterStartTime());
        cal.add(Calendar.MONTH, 3);
        return cal.getTimeInMillis() - 1000;
    }

    /**
     * 获得半年前时间
     */
    public static long getHalfYearAgoTime()
    {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        month -= 6;
        if (month < 0)
        {
            month += 12;
            cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
        }
        cal.set(Calendar.MONTH, month);
        return cal.getTimeInMillis();
    }
}
