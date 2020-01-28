package com.mplanet.testhandler;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.App;
import com.AppManager;
import com.BaseActivity;
import com.jni.JniTest;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.test.activity.TestActivityActivity;
import com.test.aidl.AidlActivity;
import com.test.angleTextView.AngleTextViewActivity;
import com.test.animation.TestAnimationActivity;
import com.test.audio.MediaPlayerActivity;
import com.test.bitmap.TestBitmapActivity;
import com.test.camera.Camera2Activity;
import com.test.camera.Camera3Activity;
import com.test.camera.CameraActivity;
import com.test.checkbox.CheckBoxActivity;
import com.test.choosedate.ChooseDateActivity;
import com.test.choosedate.ChooseDateInPopActivity;
import com.test.circlepic.TestImgActivity;
import com.test.contentprovider.ContentProviderActivity;
import com.test.countdownlatch.TestHandlerActivity2;
import com.test.deviceawake.DeviceAwakeActivity;
import com.test.dialogactivity.DialogActivity;
import com.test.expandable.ExpandableActivity;
import com.test.ftp.FtpActivity;
import com.test.handler.TestHandlerActivity;
import com.test.http.HttpActivity;
import com.test.http.OkHttpActivity;
import com.test.intentservice.RSSPullService;
import com.test.jpeg.JpegYuvRgbActivity;
import com.test.keyboardinput.InputActivity;
import com.test.loading.LoadingActivity;
import com.test.net.TestNetActivity;
import com.test.nfc.NfcActivity;
import com.test.notification.NotificationActivity;
import com.test.nsd.TestNsdActivity;
import com.test.opengles.OpenglesActivity01;
import com.test.recyclerview.RecyclerViewActivity;
import com.test.refreshandloadmore.RefreshAndLoadMoreActivity;
import com.test.regex.RegexActivity;
import com.test.service.ServiceActivity;
import com.test.sharedpreference.TestSharedpreferenceActivity;
import com.test.sqlite.SqliteActivity;
import com.test.swiperefresh.SwipeRefreshActivity;
import com.test.systemphoto.ThumbnailActivity;
import com.test.thread.ThreadActivity;
import com.test.timer.TimerActivity;
import com.test.touchevent.TouchEventActivity;
import com.test.touchevent.VelocityTrackerActivity;
import com.test.transparentactivity.TransparentActivity;
import com.test.view.BezierActivity;
import com.test.view.CutPicActivity;
import com.test.view.ImageViewActivity;
import com.test.view.TestSomeViewActivity;
import com.test.view.TrapezoidLayoutActivity;
import com.test.xml.TestXmlActivity;
import com.test.touchevent.TouchEvent2Activity;
import com.test.yuv.Mp4ToYuvActivity;
import com.test.yuv.YuvToBitmapActivity;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private final String TAG = "MainActivity";
    int options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        App.testString = "MainActivity";
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
            R.id.textview_timer, R.id.textview_imageview, R.id.textview_cutpic, R.id.textview_notification,
            R.id.textview_calendar, R.id.textview_calendarbypop, R.id.textview_bezier, R.id.textview_sqlite,
            R.id.textview_contentprovider, R.id.textview_service, R.id.textview_aidl, R.id.梯形layout,
            R.id.自定义handler, R.id.自定义view, R.id.ftp, R.id.save_sp, R.id.mediaplayer,
            R.id.yuvtobitmap, R.id.jpeg, R.id.camera2, R.id.camera3, R.id.mp4toyuv,
            R.id.okhttp, R.id.expandablelistview, R.id.loading, R.id.recyclerview, R.id.angleTextView,
            R.id.nfc})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.nfc:
                startActivity(new Intent(this, NfcActivity.class));
                break;
            case R.id.angleTextView:
                startActivity(new Intent(this, AngleTextViewActivity.class));
                break;
            case R.id.recyclerview:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
            case R.id.loading:
                startActivity(new Intent(this, LoadingActivity.class));
                break;
            case R.id.expandablelistview:
                startActivity(new Intent(this, ExpandableActivity.class));
                break;
            case R.id.okhttp:
                startActivity(new Intent(this, OkHttpActivity.class));
                break;
            case R.id.mp4toyuv:
                startActivity(new Intent(this, Mp4ToYuvActivity.class));
                break;
            case R.id.camera3:
                startActivity(new Intent(this, Camera3Activity.class));
                break;
            case R.id.camera2:
                startActivity(new Intent(this, Camera2Activity.class));
                break;
            case R.id.jpeg:
                startActivity(new Intent(this, JpegYuvRgbActivity.class));
                break;
            case R.id.yuvtobitmap:
                startActivity(new Intent(this, YuvToBitmapActivity.class));
                break;
            case R.id.mediaplayer:
                startActivity(new Intent(this, MediaPlayerActivity.class));
                break;
            case R.id.save_sp:
                startActivity(new Intent(this, TestSharedpreferenceActivity.class));
                break;
            case R.id.ftp:
                startActivity(new Intent(this, FtpActivity.class));
                break;
            case R.id.自定义view:
                startActivity(new Intent(this, TouchEvent2Activity.class));
                break;
            case R.id.自定义handler:
                startActivity(new Intent(this, com.test.handler.TestHandlerActivity2.class));
                break;
            case R.id.梯形layout:
                startActivity(new Intent(this, TrapezoidLayoutActivity.class));
                break;
            case R.id.textview_aidl:
                startActivity(new Intent(this, AidlActivity.class));
                break;
            case R.id.textview_service:
                startActivity(new Intent(this, ServiceActivity.class));
                break;
            case R.id.textview_contentprovider:
                startActivity(new Intent(this, ContentProviderActivity.class));
                break;
            case R.id.textview_sqlite:
                startActivity(new Intent(this, SqliteActivity.class));
                break;
            case R.id.textview_bezier:
                startActivity(new Intent(this, BezierActivity.class));
                break;
            case R.id.textview_calendarbypop:
                startActivity(new Intent(this, ChooseDateInPopActivity.class));
                break;
            case R.id.textview_calendar:
                startActivity(new Intent(this, ChooseDateActivity.class));
                break;
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

        Log.d(TAG, "App.testString=" + App.testString);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
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
        if (Build.VERSION.SDK_INT>=23&& ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionLists.add(Manifest.permission.RECORD_AUDIO);
        }

        if (!permissionLists.isEmpty()) {//说明肯定有拒绝的权限
            ActivityCompat.requestPermissions(this, permissionLists.toArray(new String[permissionLists.size()]), 0);
        }

    }

    private boolean isMainThread(){
        //return  Looper.getMainLooper() == Looper.myLooper();
        return  Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            AppManager.getAppManager().finishAllActivity();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}


