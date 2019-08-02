package com.test.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    public void saveSomeSP(){
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("key_string", "str");
        editor.putInt("key_int", 15);
        editor.putBoolean("key_boolean", true);
        editor.putLong("key_long", System.currentTimeMillis());
        editor.putFloat("key_float", 0.1f);
        editor.commit();
    }

    private static final List<String> blackList = new ArrayList<String>(Arrays.asList("key_int"));
    public String generateSPJsonString(){
        String json = null;
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Map<String, ?> map = preference.getAll();
        BackupPreferenceModel backupPreferenceModel = new BackupPreferenceModel();
        backupPreferenceModel.setImei("115");
        backupPreferenceModel.setUpdateTime(System.currentTimeMillis());
        for(String key : map.keySet()){
            if(null == key)
                continue;
            if(blackList.contains(key))
                continue;
            if(map.get(key) instanceof Integer){
                backupPreferenceModel.addParam(key, BackupPreferenceModel.TYPE_INT, String.valueOf((Integer) map.get(key)));
            }else if(map.get(key) instanceof Boolean){
                backupPreferenceModel.addParam(key, BackupPreferenceModel.TYPE_BOOLEAN, String.valueOf((Boolean) map.get(key)));
            }else if(map.get(key) instanceof String){
                backupPreferenceModel.addParam(key, BackupPreferenceModel.TYPE_STRING, (String)map.get(key));
            }else if(map.get(key) instanceof Float){
                backupPreferenceModel.addParam(key, BackupPreferenceModel.TYPE_FLOAT, String.valueOf((Float) map.get(key)));
            }else if(map.get(key) instanceof Long){
                backupPreferenceModel.addParam(key, BackupPreferenceModel.TYPE_LONG, String.valueOf((Long) map.get(key)));
            }
        }
        Gson gson = new Gson();
        json = gson.toJson(backupPreferenceModel);
        return json;
    }
}
