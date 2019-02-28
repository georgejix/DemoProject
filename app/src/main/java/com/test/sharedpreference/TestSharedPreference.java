package com.test.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

/**
 * Created by jix on 2019/2/28.
 */

public class TestSharedPreference {
    private Context context;

    public TestSharedPreference(Context context){
        this.context = context;
    }

    private String getUrl(){
        String url = context.getSharedPreferences("ip", Context.MODE_PRIVATE).getString("url", "");
        return url;

    }

    public void mofidyUrl(String url){
        SharedPreferences sharedPreferences = context.getSharedPreferences("ip", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("url", url);
        editor.commit();
    }
}
