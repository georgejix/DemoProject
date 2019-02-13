package com.mplanet.testhandler;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.jni.JniTest;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.test.activity.TestActivityActivity;
import com.test.animation.TestAnimationActivity;
import com.test.bitmap.TestBitmapActivity;
import com.test.camera.CameraActivity;
import com.test.checkbox.CheckBoxActivity;
import com.test.circlepic.TestImgActivity;
import com.test.countdownlatch.TestHandlerActivity2;
import com.test.deviceawake.DeviceAwakeActivity;
import com.test.dialogactivity.DialogActivity;
import com.test.handler.TestHandlerActivity;
import com.test.http.HttpActivity;
import com.test.intentservice.RSSPullService;
import com.test.keyboardinput.InputActivity;
import com.test.net.TestNetActivity;
import com.test.notification.NotificationActivity;
import com.test.nsd.TestNsdActivity;
import com.test.opengles.OpenglesActivity01;
import com.test.refreshandloadmore.RefreshAndLoadMoreActivity;
import com.test.regex.RegexActivity;
import com.test.swiperefresh.SwipeRefreshActivity;
import com.test.systemphoto.ThumbnailActivity;
import com.test.thread.ThreadActivity;
import com.test.timer.TimerActivity;
import com.test.touchevent.TouchEventActivity;
import com.test.touchevent.VelocityTrackerActivity;
import com.test.transparentactivity.TransparentActivity;
import com.test.view.CutPicActivity;
import com.test.view.ImageViewActivity;
import com.test.view.TestSomeViewActivity;
import com.test.xml.TestXmlActivity;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends Activity {
    private final String TAG = "MainActivity";
    int options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        checkPermission();
        //Log.d(TAG, "getFilesDir=" + getFilesDir().getAbsolutePath() + ",getCacheDir=" + getCacheDir().getAbsolutePath());
        //Log.d(TAG, "getExternalFilesDir=" + getExternalFilesDir("img"));
        //Log.d(TAG, "getExternalCacheDir=" + getExternalCacheDir().getAbsolutePath() + ",getCacheDir=" + getCacheDir().getAbsolutePath());

        options = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                // | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(options);
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                Log.d(TAG, "OnSystemUiVisibilityChangeListener");
            }
        });
    }

    @OnClick(value = {R.id.textview_next_page, R.id.textview_testactivity, R.id.textview_testcamera,
            R.id.textview_testhandler, R.id.textview_testbitmap, R.id.textview_testopengles,
            R.id.textview_testanimation, R.id.textview_testnsd, R.id.textview_testnet,
            R.id.textview_testxml, R.id.textview_testfullscreen, R.id.textview_testimg,
            R.id.textview_testswiperefresh, R.id.textview_testrefreshandloadmore,
            R.id.textview_testregex, R.id.textview_testmotionevent, R.id.textview_testvelocitytracker,
            R.id.textview_testinput, R.id.textview_testintentservice, R.id.textview_testsystemphoto,
            R.id.textview_testdeviceawake, R.id.textview_testjni, R.id.textview_transparant, R.id.textview_dialog,
            R.id.textview_checkbox, R.id.textview_someview, R.id.textview_http, R.id.textview_thread,
            R.id.textview_timer, R.id.textview_imageview, R.id.textview_cutpic, R.id.textview_notification})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.textview_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.textview_cutpic:
                startActivity(new Intent(this, CutPicActivity.class));
                break;
            case R.id.textview_imageview:
                startActivity(new Intent(this, ImageViewActivity.class));
                break;
            case R.id.textview_timer:
                startActivity(new Intent(this, TimerActivity.class));
                break;
            case R.id.textview_thread:
                startActivity(new Intent(this, ThreadActivity.class));
                break;
            case R.id.textview_http:
                startActivity(new Intent(this, HttpActivity.class));
                break;
            case R.id.textview_someview:
                startActivity(new Intent(this, TestSomeViewActivity.class));
                break;
            case R.id.textview_checkbox:
                startActivity(new Intent(this, CheckBoxActivity.class));
                break;
            case R.id.textview_dialog:
                startActivity(new Intent(MainActivity.this, DialogActivity.class));
                break;
            case R.id.textview_transparant:
                startActivity(new Intent(MainActivity.this, TransparentActivity.class));
                break;
            case R.id.textview_testjni:
                JniTest j = new JniTest();
                j.test();
                break;
            case R.id.textview_testdeviceawake:
                startActivity(new Intent(MainActivity.this, DeviceAwakeActivity.class));
                break;
            case R.id.textview_testsystemphoto:
                startActivity(new Intent(MainActivity.this, ThumbnailActivity.class));
                break;
            case R.id.textview_testintentservice:
                Intent rssPullServiceIntent = new Intent(MainActivity.this, RSSPullService.class);
                rssPullServiceIntent.setData(Uri.parse("www.baidu.com"));
                startService(rssPullServiceIntent);
                break;
            case R.id.textview_testinput:
                startActivity(new Intent(MainActivity.this, InputActivity.class));
                break;
            case R.id.textview_testvelocitytracker:
                startActivity(new Intent(MainActivity.this, VelocityTrackerActivity.class));
                break;
            case R.id.textview_testmotionevent:
                startActivity(new Intent(MainActivity.this, TouchEventActivity.class));
                break;
            case R.id.textview_testregex:
                startActivity(new Intent(MainActivity.this, RegexActivity.class));
                break;
            case R.id.textview_testrefreshandloadmore:
                startActivity(new Intent(MainActivity.this, RefreshAndLoadMoreActivity.class));
                break;
            case R.id.textview_testswiperefresh:
                startActivity(new Intent(MainActivity.this, SwipeRefreshActivity.class));
                break;
            case R.id.textview_testimg:
                startActivity(new Intent(MainActivity.this, TestImgActivity.class));
                break;
            case R.id.textview_testfullscreen:
                if (options != getWindow().getDecorView().getSystemUiVisibility()) {
                    getWindow().getDecorView().setSystemUiVisibility(options);
                } else {
                    int o = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                    getWindow().getDecorView().setSystemUiVisibility(o);
                }
                break;
            case R.id.textview_testxml:
                startActivity(new Intent(MainActivity.this, TestXmlActivity.class));
                break;
            case R.id.textview_testnet:
                startActivity(new Intent(MainActivity.this, TestNetActivity.class));
                break;
            case R.id.textview_testnsd:
                startActivity(new Intent(MainActivity.this, TestNsdActivity.class));
                break;
            case R.id.textview_testanimation:
                startActivity(new Intent(MainActivity.this, TestAnimationActivity.class));
                break;
            case R.id.textview_testopengles:
                startActivity(new Intent(MainActivity.this, OpenglesActivity01.class));
                break;
            case R.id.textview_testbitmap:
                startActivity(new Intent(MainActivity.this, TestBitmapActivity.class));
                break;
            case R.id.textview_testhandler:
                startActivity(new Intent(MainActivity.this, TestHandlerActivity2.class));
                break;
            case R.id.textview_testcamera:
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
                break;
            case R.id.textview_testactivity:
                startActivity(new Intent(MainActivity.this, TestActivityActivity.class));
                break;
            case R.id.textview_next_page:
                startActivity(new Intent(MainActivity.this, TestHandlerActivity.class));
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TestActivity", "MainActivity");
        getWindow().getDecorView().setSystemUiVisibility(options);
        /*System.out.println(TAG + "," + nextPage.getWidth());
        nextPage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

            @Override
            public void onGlobalLayout() {
                if(Build.VERSION.SDK_INT >= 16) {
                    nextPage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }else{
                    nextPage.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                System.out.println(TAG + ",addOnGlobalLayoutListener:" + nextPage.getWidth());
            }
        });

        nextPage.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                nextPage.getViewTreeObserver().removeOnPreDrawListener(this);
                System.out.println(TAG + ",addOnPreDrawListener:" + nextPage.getWidth());
                return false;
            }
        });

        nextPage.post(new Runnable() {
            @Override
            public void run() {
                System.out.println(TAG + ",post:" + nextPage.getWidth());
            }
        });

        nextPage.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                nextPage.removeOnLayoutChangeListener(this);
                System.out.println(TAG + ",addOnLayoutChangeListener:" + nextPage.getWidth());
            }
        });*/

    }

    private void checkPermission() {
        List<String> permissionLists = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionLists.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionLists.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionLists.add(Manifest.permission.CAMERA);
        }
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionLists.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!permissionLists.isEmpty()) {//说明肯定有拒绝的权限
            ActivityCompat.requestPermissions(this, permissionLists.toArray(new String[permissionLists.size()]), 0);
        }

    }

    private boolean isMainThread(){
        //return  Looper.getMainLooper() == Looper.myLooper();
        return  Looper.getMainLooper().getThread() == Thread.currentThread();
    }

}


