package com;

import android.app.Application;
import android.os.Environment;

import java.io.File;

public class App extends Application {
    private File projectFile;
    static String path;

    //测试当activity的process不同时，数据不共享
    public static String testString = "App";

    public App(){
        super();
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "demoproject";
        if(null != path){
            projectFile = new File(path);
            if(null != projectFile){
                if(!projectFile.exists()){
                    projectFile.mkdirs();
                }
            }
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }

    public static String getFile(){
     return null == path ? Environment.getExternalStorageDirectory().getAbsolutePath() : path;
    }
}
