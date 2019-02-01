package com.test.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
*Createdbyjixon2019/2/1.
*/

public class HttpURLConnectionUtil{

    private static HttpURLConnectionUtil httpUtils;
    public static HttpURLConnectionUtil getInstance(){
        if(null == httpUtils){
            synchronized (HttpURLConnectionUtil.class){
                if(null == httpUtils){
                    httpUtils = new HttpURLConnectionUtil();
                }
            }
        }
        return httpUtils;
    }

public byte[] sendGet(String url_path){
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        byte b[] = null;

        try {
            //根据URL地址实例化一个URL对象，用于创建HttpURLConnection对象。
            URL url = new URL(url_path);

            if (url != null) {
                //openConnection获得当前URL的连接
                httpURLConnection = (HttpURLConnection) url.openConnection();
                //设置3秒的响应超时
                httpURLConnection.setConnectTimeout(3000);
                //设置允许输入
                httpURLConnection.setDoInput(true);
                //设置为GET方式请求数据
                httpURLConnection.setRequestMethod("GET");
                //获取连接响应码，200为成功，如果为其他，均表示有问题
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    //getInputStream获取服务端返回的数据流。
                    inputStream = httpURLConnection.getInputStream();
                    //bitmap = BitmapFactory.decodeStream(inputStream);
                    b = changeInputStreamToByteArray(inputStream,
                            "utf-8");
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null != httpURLConnection) {
                httpURLConnection.disconnect();
            }
        }
        return b;
    }

    /**
     * 通过给定的请求参数和编码格式，获取服务器返回的数据
     * @param params 请求参数
     * @param encode 编码格式
     * @return 获得的字符串
     */
    public String sendPostMessage(String url_path, Map<String, String> params,
                                         String encode) {
        if(null == url_path || null == params || null == encode){
            return null;
        }
        HttpURLConnection urlConnection = null;
        StringBuffer buffer = new StringBuffer();
        if (!params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    buffer.append(entry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), encode))
                            .append("&");//请求的参数之间使用&分割。
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        }
            buffer.deleteCharAt(buffer.length() - 1);
            System.out.println(buffer.toString());
            try {
                URL url = new URL(url_path);
                urlConnection = (HttpURLConnection) url
                        .openConnection();
                urlConnection.setConnectTimeout(3000);
                //设置允许输入输出
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                byte[] mydata = buffer.toString().getBytes();
                //设置请求报文头，设定请求数据类型
                urlConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                //设置请求数据长度
                urlConnection.setRequestProperty("Content-Length",
                        String.valueOf(mydata.length));
                //设置POST方式请求数据
                urlConnection.setRequestMethod("POST");
                OutputStream outputStream = urlConnection.getOutputStream();
                outputStream.write(mydata);
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    return changeInputStream(urlConnection.getInputStream(),
                            encode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(null != urlConnection) {
                    urlConnection.disconnect();
                }
            }
        return null;
    }

    /**
     * 把服务端返回的输入流转换成字符串格式
     * @param inputStream 服务器返回的输入流
     * @param encode 编码格式
     * @return 解析后的字符串
     */
    private String changeInputStream(InputStream inputStream,
                                            String encode) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result="";
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    outputStream.write(data,0,len);
                }
                result=new String(outputStream.toByteArray(),encode);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private byte[] changeInputStreamToByteArray(InputStream inputStream,
                                            String encode) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        byte b[] = null;
        int len = 0;
        String result="";
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    outputStream.write(data,0,len);
                }
                b = outputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return b;
    }
}
