package com.test.util;

import org.apache.http.client.methods.HttpRequestBase;

/**
 * Created by jix on 2018/3/27.
 */

public class HttpHeaderUtil {


    static HttpHeaderUtil httpHeaderUtil = new HttpHeaderUtil();

    public static HttpHeaderUtil getInstance(){
        return httpHeaderUtil;
    }


    public void addHeader(HttpRequestBase httpRequst){
        String host = httpRequst.getURI().getHost();
        if(null == host || host.trim().isEmpty()){
            return;
        }
        httpRequst.setHeader("Tp-Username", "");
        httpRequst.setHeader("Tp-Session", "");
    }

}
