package com.test.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * Created by Administrator on 2018/12/21.
 */

public class HttpUtils {
    private static HttpUtils httpUtils;
    public static HttpUtils getInstance(){
        if(null == httpUtils){
            synchronized (HttpUtils.class){
                if(null == httpUtils){
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }

    /**
     *
     * @return
     * @brief httpget
     */
    public String sendGetMessage(String httpURL, String entityString) {
        try {

            HttpClient client = new DefaultHttpClient();
            client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,2000);//连接时间
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,2000);//数据传输时间
            //发送get请求
            HttpGet request = new HttpGet(httpURL);
            HttpHeaderUtil.getInstance().addHeader(request);
            HttpResponse httpResponse = client.execute(request);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                String data = EntityUtils.toString(httpEntity, HTTP.UTF_8);
                return data;
            }
        } catch (UnsupportedEncodingException e) {
        } catch (ClientProtocolException e) {
        } catch (ParseException e) {
        } catch (IOException e) {
        }
        return null;
    }

    /**
     *
     * @return
     * @brief httpput
     */
    public String sendPutMessage(String httpURL, String entityString) {
        if(null == entityString){
            return null;
        }
        try {

            HttpClient client = new DefaultHttpClient();
            HttpPut request = new HttpPut(httpURL);
            StringEntity entity = new StringEntity(entityString, "UTF-8");
            request.setEntity(entity);
            HttpHeaderUtil.getInstance().addHeader(request);
            HttpResponse httpResponse = client.execute(request);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                String data = EntityUtils.toString(httpEntity, HTTP.UTF_8);
                return data;
            }
        } catch (UnsupportedEncodingException e) {
        } catch (ClientProtocolException e) {
        } catch (ParseException e) {
        } catch (IOException e) {
        }
        return null;
    }

    /**
     *
     * @param httpURL
     * @param entityString
     * @return
     * @brief
     */
    public String sendPostMessage(String httpURL, String entityString) {
        if(null == entityString){
            return null;
        }
        try {
            HttpPost httpRequst = new HttpPost(httpURL);// 创建HttpPost对象
            // 组装数据
            StringEntity entity = new StringEntity(entityString, "UTF-8");
            httpRequst.setEntity(entity);
            HttpHeaderUtil.getInstance().addHeader(httpRequst);
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                String data = EntityUtils.toString(httpEntity, HTTP.UTF_8);
                return data;
            }
        } catch (UnsupportedEncodingException e) {
        } catch (ClientProtocolException e) {
        } catch (ParseException e) {
        } catch (IOException e) {
        }
        return null;
    }

    /**
     *
     * @return
     * @brief httpdelete
     */
    public String sendDeleteMessage(String httpURL, String entityString) {
        if(null == entityString){
            return null;
        }
        try {
            HttpDeleteWithBody httpRequst = new HttpDeleteWithBody(httpURL);// 创建HttpPost对象
            // 组装数据
            StringEntity entity = new StringEntity(entityString, "UTF-8");
            httpRequst.setEntity(entity);
            HttpHeaderUtil.getInstance().addHeader(httpRequst);
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                String data = EntityUtils.toString(httpEntity, HTTP.UTF_8);
                return data;
            }
        } catch (UnsupportedEncodingException e) {
        } catch (ClientProtocolException e) {
        } catch (ParseException e) {
        } catch (IOException e) {
        }
        return null;
    }

}
