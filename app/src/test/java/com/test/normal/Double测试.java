package com.test.normal; /**
 *
 */

import android.text.TextUtils;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * @brief
 * @details
 * @author Administrator
 *
 */
public class Double测试
{
    @Test
    public void main()
    {
        System.out.println((int) Math.ceil(360 * 1 / 6000));
        System.out.println(filterUnUsefulAccuracy("100.0000120s"));
        System.out.println(filterUnUsefulAccuracy(100.0000120));
        System.out.println(filterAccuracyToString(100.0000120, 2));
        System.out.println(filterAccuracyToString("100.0000120", 2));
        System.out.println(filterAccuracyToDouble(100.0000120, 2) + "");
        System.out.println(filterAccuracyToDouble(100.0000120, 2) + "");
        System.out.println(filterAccuracyToString(999999900, 2));
        System.out.println(filterUnUsefulAccuracy(0.01));
    }

    /**
     * 去掉末尾无用的0
     * @param value
     * @return
     */
    private static String filterUnUsefulAccuracy(String value)
    {
        try
        {
            double d = Double.parseDouble(value);
            if (0d == d - ((int) d))
            {
                return String.valueOf((int) d);
            }
            double d2 = Double.valueOf(value.replaceAll("0*$", ""));
            if (d == d2)
            {
                NumberFormat nf = NumberFormat.getInstance();
                nf.setGroupingUsed(false);
                return nf.format(d2);
            }
        } catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return value;
    }

    private static String filterUnUsefulAccuracy(double value)
    {
        try
        {
            String valueStr = String.valueOf(value);
            double d = Double.parseDouble(valueStr);
            if (0d == d - ((int) d))
            {
                return String.valueOf((int) d);
            }
            double d2 = Double.valueOf(valueStr.replaceAll("0*$", ""));
            if (d == d2)
            {
                NumberFormat nf = NumberFormat.getInstance();
                nf.setGroupingUsed(false);
                return nf.format(d2);
            }
        } catch (Exception e)
        {
        }
        return String.valueOf(value);
    }
    //BigDecimal a=new BigDecimal(d);
    //return a.stripTrailingZeros().toPlainString();

    /**
     * 过滤精度
     *
     * @param value
     * @param accuracy
     * @return
     */
    public static String filterAccuracyToString(double value, int accuracy)
    {
        if (accuracy <= 0)
        {
            return String.valueOf(value);
        }
        BigDecimal bd = new BigDecimal(value);
        double result = bd.setScale(accuracy, BigDecimal.ROUND_HALF_UP).doubleValue();
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        return nf.format(result);
    }

    /**
     * 过滤精度
     *
     * @param value
     * @param accuracy
     * @return
     */
    public static double filterAccuracyToDouble(double value, int accuracy)
    {
        if (accuracy <= 0)
        {
            return value;
        }
        BigDecimal bd = new BigDecimal(value);
        double result = bd.setScale(accuracy, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }

    /**
     * 过滤精度
     *
     * @param value
     * @param accuracy
     * @return
     */
    public static String filterAccuracyToString(String value, int accuracy)
    {
        if (null == value || accuracy <= 0)
        {
            return value;
        }
        try
        {
            BigDecimal bd = new BigDecimal(value);
            double result = bd.setScale(accuracy, BigDecimal.ROUND_HALF_UP).doubleValue();
            NumberFormat nf = NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            return nf.format(result);
        } catch (Exception e)
        {
        }
        return value;
    }

    /**
     * 过滤精度
     *
     * @param value
     * @param accuracy
     * @return
     */
    public static double filterAccuracyToDouble(String value, int accuracy)
    {
        if (null == value || accuracy <= 0)
        {
            return 0d;
        }
        try
        {
            BigDecimal bd = new BigDecimal(value);
            double result = bd.setScale(accuracy, BigDecimal.ROUND_HALF_UP).doubleValue();
            return result;
        } catch (Exception e)
        {
        }
        return 0d;
    }
}
