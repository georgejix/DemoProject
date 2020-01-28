package com.test.sharedpreference;

import java.util.ArrayList;
import java.util.List;

public class BackupPreferenceModel {
    public static final String TYPE_INT = "int";
    public static final String TYPE_BOOLEAN = "boolean";
    public static final String TYPE_FLOAT = "float";
    public static final String TYPE_STRING = "String";
    public static final String TYPE_LONG = "long";

    private long updateTime;
    private String imei;
    private List<Param> params;

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    public void addParam(String key, String type, String value){
        if(null == params){
            params = new ArrayList<>();
        }
        if(null == key || null == type || null == value)
            return;
        params.add(new Param(key, type, value));
    }

    public class Param{
        Param(String key, String type, String value){
            this.key = key;
            this.type = type;
            this.value = value;
        }
        private String key;
        private String type;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
