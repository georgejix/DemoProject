package com.test.deviceawake;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.mplanet.testhandler.R;

import java.util.Calendar;
import java.util.Locale;

/**
 * alarmmanager开始后，app清除后台，仍然可以生效
 * ELAPSED_REALTIME：从设备启动之后开始算起，度过了某一段特定时间后，激活Pending Intent，但不会唤醒设备。其中设备睡眠的时间也会包含在内。
 * ELAPSED_REALTIME_WAKEUP：从设备启动之后开始算起，度过了某一段特定时间后唤醒设备。
 * RTC：在某一个特定时刻激活Pending Intent，但不会唤醒设备。
 * RTC_WAKEUP：在某一个特定时刻唤醒设备并激活Pending Intent。
 */
@ContentView(R.layout.activity_device_awake)
public class DeviceAwakeActivity extends Activity {
    private final String TAG = "DeviceAwakeActivity";

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
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
        //calendar.set(Calendar.HOUR_OF_DAY, 15);
        //calendar.set(Calendar.MINUTE, 40);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), alarmIntent);
        Log.d(TAG, "start alarmmanager");
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
