package com.test.regex;

import android.app.Activity;
import android.os.Bundle;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.mplanet.testhandler.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ContentView(R.layout.activity_regex)
public class RegexActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
    }

    private String selectNumber(String str) {
        if(null != str) {
            return str.replaceAll("[^0-9]", "");
        }
        return str;
    }

    private String operateOnResultStr(String str){
        if(null == str){
            return str;
        }
        // 邮箱验证规则
        //String regEx = "(sn=([A-Z0-9]*)#)";
        String regEx = "(/([A-Za-z0-9]+)\\?sn=([A-Z0-9]*)#)";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.find();
        if(rs) {
            return matcher.group(2) + matcher.group(3);//参数即括号
        }
        return str;
    }
}
