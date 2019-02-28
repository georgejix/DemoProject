package com.test.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by jix on 2019/2/28.
 */

public class MessengerService extends Service {
    private final String TAG = "MessengerService";

    private class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1001:
                    Log.d(TAG, msg.getData().getString("msg"));
                    Messenger client = msg.replyTo;
                    Message message = Message.obtain(null, 1002);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "this is service");
                    message.setData(bundle);
                    try {
                        client.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
