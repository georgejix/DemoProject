package com.test.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.mplanet.testhandler.MainActivity;
import com.mplanet.testhandler.R;

import java.util.Timer;
import java.util.TimerTask;

@ContentView(R.layout.activity_notification)
public class NotificationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
    }

    public void notification2(View view){
        initNotification();
        if(null != notificationManager && null != notification) {
            String title = null;
            String msg = System.currentTimeMillis() + "";
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.checkbox_checked);
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setContentText(null == msg ? "" : msg)
                    .setContentTitle(null == title ? getResources().getString(R.string.app_name) : title)
                    .setSmallIcon(R.mipmap.checkbox_checked)
                    .setLargeIcon(bitmap);

            //相同id,但tag不同，通知时，不会被覆盖
            notificationManager.notify(System.currentTimeMillis() + "", 1, builder.build());
        }
    }

    public void notification(View view){
        initNotification();
        if(null != notificationManager && null != notification && null != builder) {
            String title = null;
            String msg = System.currentTimeMillis() + "";
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.checkbox_checked);
            Intent intent = new Intent(this, NotificationActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setContentText(null == msg ? "" : msg)
                    .setContentTitle(null == title ? getResources().getString(R.string.app_name) : title)
                    .setSmallIcon(R.mipmap.checkbox_checked)
                    .setLargeIcon(bitmap);
            //builder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.camille));

            notificationManager.notify(System.currentTimeMillis() + "" , 1, builder.build());
        }
    }

    int i = 0;
    TimerTask timerTask = null;
    public void updatenotification(View view) {
        initUpdateNotification();
        i = 0;
        Timer timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if(i < 100) {
                    update(100, i++, false);
                }else{
                    update(0, 0, false);
                }
            }
        };
        timer.schedule(timerTask, 1000, 50);
    }

    private void update(int max, int progress, boolean centern){
        if(null != notificationManager && null != updateNotification && null != updateBuilder) {
            String title = null;
            String msg = System.currentTimeMillis() + "";
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.checkbox_checked);
            Intent intent = new Intent(this, NotificationActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            updateBuilder.setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    //.setContentText(null == msg ? "" : msg)
                    .setContentTitle(null == title ? getResources().getString(R.string.app_name) : title)
                    .setSmallIcon(R.mipmap.checkbox_checked)
                    .setLargeIcon(bitmap);
            updateBuilder.setProgress(max, progress, centern);

            notificationManager.notify(2, updateBuilder.build());
        }
        if(0 == max && 0 == progress && !centern){
            timerTask.cancel();
            notificationManager.cancel(2);
        }
    }

    Notification.Builder builder;
    NotificationManager notificationManager;
    Notification notification;
    private void initNotification() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (notification == null) {
            if (Build.VERSION.SDK_INT >= 26) {
                String channelID = getPackageName() + "1";
                String channelName = "alarm";
                NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
                channel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ring), Notification.AUDIO_ATTRIBUTES_DEFAULT);
                notificationManager.createNotificationChannel(channel);
                builder = new Notification.Builder(getApplicationContext());

                /*builder.setContentText("");
                builder.setContentTitle(getApplicationContext().getResources().getString(R.string.app_name));
                builder.setSmallIcon(R.mipmap.checkbox_checked);
                Bitmap icon =  BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.checkbox_checked);
                builder.setLargeIcon(icon);*/

                //创建通知时指定channelID
                builder.setChannelId(channelID);
                notification = builder.build();

            } else {
                builder = new Notification.Builder(getApplicationContext());
                notification = builder
                        .setContentTitle(getApplicationContext().getResources().getString(R.string.app_name))
                        .setSmallIcon(R.mipmap.checkbox_checked).setWhen(System.currentTimeMillis()).build();
                notification.flags = Notification.FLAG_ONGOING_EVENT;
            }
        }
    }


    Notification.Builder updateBuilder;
    Notification updateNotification;
    private void initUpdateNotification() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (updateNotification == null) {
            if (Build.VERSION.SDK_INT >= 26) {
                String channelID = getPackageName() + "2";
                String channelName = "update";
                NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_LOW);
                channel.setSound(null, null);
                notificationManager.createNotificationChannel(channel);
                updateBuilder = new Notification.Builder(getApplicationContext());

                /*builder.setContentText("");
                builder.setContentTitle(getApplicationContext().getResources().getString(R.string.app_name));
                builder.setSmallIcon(R.mipmap.checkbox_checked);
                Bitmap icon =  BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.checkbox_checked);
                builder.setLargeIcon(icon);*/

                //创建通知时指定channelID
                updateBuilder.setChannelId(channelID);
                updateNotification = updateBuilder.build();

            } else {
                updateBuilder = new Notification.Builder(getApplicationContext());
                updateNotification = updateBuilder
                        .setContentTitle(getApplicationContext().getResources().getString(R.string.app_name))
                        .setSmallIcon(R.mipmap.checkbox_checked).setWhen(System.currentTimeMillis()).build();
                updateNotification.flags = Notification.FLAG_ONGOING_EVENT;
            }
        }
    }
}
