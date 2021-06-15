package com.test.normal;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */

/**
 * @brief
 * @details
 * @author Administrator
 *
 */
public class 正则表达式
{
    @Test
    public void main()
    {
        正则表达式 main = new 正则表达式();
        System.out.println(
                main.operateOnResultStr("http://www.anjugroup.cn/NG01?sn=CXAA18CAA0100552#0C46AC001A51"));
		 /*System.out.println(
				 main.testZhongWen("sas"));*/
        //main.test01();
        /*System.out.println(main.operateOnResultStr("http://www.anjugroup.cn/open?sn=WAT025353000000000000001"));
        System.out.println(main.operateOnResultStr("http://www.anjugroup.cn/WAT01?sn=CXAA18CAA0101080#"));
        System.out.println(selectNumber("130 1300 1300"));
        filter2("213函数AASA2131\\,,");*/
        main.test2();
    }

    private void test2(){
        String regEx = "^\\d*$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher("1231231");
        System.out.println(matcher.find());
    }

    //过滤数字，字母，汉字
    private void filter2(String str)
    {
        String regEx = "[^(a-zA-Z0-9\\u4e00-\\u9fa5)]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        boolean rs = matcher.find();
        if(rs){
            System.out.println("invalid: " + matcher.group(0));
        }else {
            System.out.println("valid");
        }
        System.out.println("valid".substring(0, 12));

    }

    private String testZhongWen(String str)
    {
        String regEx = "([\\u4e00-\\u9fa5]*)";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        boolean rs = matcher.find();
        if (rs)
        {
            return matcher.group(0);
        }
        return str + "1";
    }

    private String operateOnResultStr(String str)
    {
        // 邮箱验证规则
        //String regEx = "(sn=([两个接口函数重名的实现-Z0-9]*)#)";
        String regEx = "(/([A-Za-z0-9]+)\\?sn=([A-Z0-9]*)#)";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.find();
        if (rs)
        {
            return matcher.group(2) + matcher.group(3);
        }
        return str;
    }

    private void test01()
    {
        try
        {
            Pattern pictureUrlPattern = Pattern.compile("(.*)<a>(.*)_(\\d)+(.jpg)</a>");
				/*Matcher match = pictureUrlPattern.matcher("\u5c0f\u77b3MP-FF18000051\u57282018\08\06 16:50:22\u68c0"
						+ "\u6d4b\u5230\u4e00\u6b21\u9707\u52a8\uff0c\u8bf7\u60a8\u53ca\u65f6\u786e\u8ba4\u8f66"
						+ "\u8f86\u72b6\u51b5\u662f\u5426\u5f02\u5e38<a>http://"
						+ "dev.mplanet.cn/uploadfiles/share/2018/08/06/10000DCA-07149F5E-01E90845-5B679B38_4.jpg</a>");*/
            String s = "\u5c0f\u77b3MP-FF18000051\u57282018\08\06 16:50:22\u68c0"
                    + "\u6d4b\u5230\u4e00\u6b21\u9707\u52a8\uff0c\u8bf7\u60a8\u53ca\u65f6\u786e\u8ba4\u8f66"
                    + "\u8f86\u72b6\u51b5\u662f\u5426\u5f02\u5e38";
            Matcher match = pictureUrlPattern.matcher(s);
            if (match.find())
            {
                System.out.println(match.group(1));
                String number = match.group(3);
                int num = Integer.parseInt(number);
                for (int i = 1; i < num + 1; i++)
                {
                    String url = match.group(2) + "_" + i + match.group(4);
                    //thumbnails.add(url);
                }
                //((EventMessage) message).setPictures(thumbnails);
            }
            else
            {
                System.out.println(s);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private String selectNumber(String s)
    {
        if (null != s)
        {
            return s.replaceAll("[^0-9]", "");
        }
        return s;
    }
}
