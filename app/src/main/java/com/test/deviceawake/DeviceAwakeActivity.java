package com.test.deviceawake;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.mplanet.testhandler.R;

import java.util.Calendar;

@ContentView(R.layout.activity_device_awake)
public class DeviceAwakeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        //保持屏幕长亮
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //持续唤醒cpu
        /*PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelockTag");
        wakeLock.acquire();
        wakeLock.release();*/

        //某个固定时间，发送一条广播（一次性或间断性发送）
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 40);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), alarmIntent);
        //重复闹钟
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
        //        1000 * 60 * 20, alarmIntent);
        //取消闹钟
        // If the alarm has been set, cancel it.
        /*if (alarmManager!= null) {
            alarmManager.cancel(alarmIntent);
        }*/

        //修改receiver在配置文件中android:enabled="false"属性
        /*ComponentName receiver = new ComponentName(getApplicationContext(), SampleBootReceiver.class);
        PackageManager pm = getApplicationContext().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);*/
    }
}
