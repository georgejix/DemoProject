package com.test.http;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;

import com.BaseActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.mplanet.testhandler.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@ContentView(R.layout.activity_ok_http)
public class OkHttpActivity extends BaseActivity implements Handler.Callback {
    private final static String TAG = "OkHttpActivity";

    private Handler mHandler;
    private final static int POST = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        mHandler.sendEmptyMessage(POST);
    }

    private void init(){
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper(), this);
    }

    private void post(){
        String url = "http://vega.huoyunren.com/zuul/face/verify_and_sign";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("orgcode", "123")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = null;
        String responseBody = null;
        try {
            response = client.newCall(request).execute();
            final int errorCode = response.code();
            responseBody = response.body().string();
            Log.v(TAG, "searchFace: code = " + errorCode + " response = " + responseBody);
            Log.d(TAG, response.isSuccessful() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void post2(){
        String url = "http://vega.huoyunren.com/zuul/face/verify_and_sign";
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(3, TimeUnit.SECONDS).build();
        RequestBody body = new FormBody.Builder()
                .add("orgcode", "123")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "error!!!    " + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(TAG, response.toString());
                    Log.d(TAG, "code=" + response.code());
                    Log.d(TAG, "message=" + response.message());
                    Log.d(TAG, "body=" + response.body().string());
                    Log.d(TAG, "success=" + response.isSuccessful());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case POST:
                post2();
                break;
        }
        return true;
    }
}
