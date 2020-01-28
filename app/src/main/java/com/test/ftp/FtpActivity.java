package com.test.ftp;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.App;
import com.BaseActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mplanet.testhandler.R;

import java.io.File;
import java.io.UnsupportedEncodingException;

@ContentView(R.layout.activity_ftp)
public class FtpActivity extends BaseActivity implements Handler.Callback {
    private final static String TAG = "FtpActivity";

    private FtpUtil ftpUtil;
    private Handler handler;

    private final int UPLOAD = 1001;

    static FtpTransferListener transferListener = new FtpTransferListener() {
        @Override
        public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
            Log.d(TAG, "totalBytesTransferred=" + totalBytesTransferred + ",bytesTransferred=" +
                    bytesTransferred + ",streamSize=" + streamSize);
            //totalBytesTransferred:总传输大小
            //bytesTransferred：本次传输大小
        }
    };

    @OnClick(value = {R.id.textview_ftp_upload})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.textview_ftp_upload:
                sendUpload();
                break;
        }
    }

    private void sendUpload(){
        if(null == handler) {
            HandlerThread handlerThread = new HandlerThread(TAG);
            handlerThread.start();
            handler = new Handler(handlerThread.getLooper(), this);
        }
        handler.sendEmptyMessage(UPLOAD);
    }

    private void upload(){
        ftpUtil = FtpUtil.getInstance();
        if(null == ftpUtil){
            return;
        }
        ftpUtil.registerTransferListener(transferListener);
        ftpUtil.ftpConnect("207.246.112.194", "userftp", "jixiao1", 12200);
        ftpUtil.ftpUpload(App.getFile() + File.separator + "文件", "wenjian", "dir1");
        /*try {
            ftpUtil.ftpUpload(App.getFile() + File.separator + "文件", new String("文件".getBytes("UTF-8"), "iso-8859-1"), "dir1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        ftpUtil.ftpDisconnect();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case UPLOAD:
                upload();
                break;
        }
        return false;
    }
}
