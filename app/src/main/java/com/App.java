package com;

import android.app.Application;
import android.os.Environment;

import java.io.File;

public class App extends Application {
    private File projectFile;
    static String path;

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

    public static String getFile(){
     return null == path ? Environment.getExternalStorageDirectory().getAbsolutePath() : path;
    }
}
