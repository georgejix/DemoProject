package com.test.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jix on 2019/2/21.
 */

public class SQLiteDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLiteDbHelper";

    public static final String DB_NAME = "database.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_STUDENT = "students";
    //创建 students 表的 sql 语句
    private static final String STUDENTS_CREATE_TABLE_SQL = "create table " + TABLE_STUDENT + "("
            + "id integer primary key autoincrement,"
            + "name varchar(20) not null,"
            //+ "aaa varchar(20) default '2',"
            + "tel_no varchar(11) not null,"
            + "cls_id integer not null"
            + ");";

    public SQLiteDbHelper(Context context) {
        // 传递数据库名与版本号给父类
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // 在这里通过 db.execSQL 函数执行 SQL 语句创建所需要的表
        // 创建 students 表
        db.execSQL(STUDENTS_CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 数据库版本号变更会调用 onUpgrade 函数，在这根据版本号进行升级数据库
        switch (oldVersion) {
            case 1:
                // do something
                break;

            default:
                break;
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onDowngrade");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // 启动外键
            db.execSQL("PRAGMA foreign_keys = 1;");

            //或者这样写
            String query = String.format("PRAGMA foreign_keys = %s", "ON");
            db.execSQL(query);
        }
    }
}
