package com.test.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.test.sqlite.SQLiteDbHelper;

import java.util.Random;

/**
 * Created by jix on 2019/2/27.
 * 相当于暴露了一个uri，可供当前app和其他app调用，Androidmenifest.xml中的provider将uri和当前类做了对应，然后通过mUriMatcher做匹配
 */
//在其他app中通过以下方式调用
/*Uri uri1 = Uri.parse("content://com.mplanet.testhandler/students/insert");
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", "11");
        contentValues.put("name", "11");
        contentValues.put("tel_no", "11");
        contentValues.put("cls_id", "11");
        //获得ContentResolver对象，调用方法
        getContentResolver().insert(uri1,contentValues);*/
//需要申请这两个自定义权限
//<uses-permission android:name="com.mplanet.testhandler.READ_CONTENT"/>
//<uses-permission android:name="com.mplanet.testhandler.WRITE_CONTENT"/>

public class DemoContentProvider extends ContentProvider{
    private final String TAG = "DemoContentProvider";
    private SQLiteDatabase mDatabase;
    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final String AUTHORITY = "com.mplanet.testhandler";
    public static final Uri URI_CONTENT = Uri.parse("content://" + AUTHORITY + "/" + SQLiteDbHelper.TABLE_STUDENT);

    //rule
    private static final int INSERT = 0;
    private static final int DELETE = 1;
    private static final int QUERY = 2;
    private static final int UPDATE = 3;


    //写入主机名的匹配规则
    static {
        mUriMatcher.addURI(AUTHORITY, SQLiteDbHelper.TABLE_STUDENT + "/insert", INSERT);
        mUriMatcher.addURI(AUTHORITY, SQLiteDbHelper.TABLE_STUDENT + "/delete", DELETE);
        mUriMatcher.addURI(AUTHORITY, SQLiteDbHelper.TABLE_STUDENT + "/query", QUERY);
        mUriMatcher.addURI(AUTHORITY, SQLiteDbHelper.TABLE_STUDENT + "/update", UPDATE);
    }

    //对外提供的匹配规则
    public interface URI{
        Uri INSERT = Uri.parse("content://"+AUTHORITY+"/"+SQLiteDbHelper.TABLE_STUDENT +"/insert");
        Uri DELETE = Uri.parse("content://"+AUTHORITY+"/"+SQLiteDbHelper.TABLE_STUDENT +"/delete");
        Uri QUERY = Uri.parse("content://"+AUTHORITY+"/"+SQLiteDbHelper.TABLE_STUDENT +"/query");
        Uri UPDATE = Uri.parse("content://"+AUTHORITY+"/"+SQLiteDbHelper.TABLE_STUDENT +"/update");
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "oncreate");
        initProvider();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Log.d(TAG, "query");
        if (mUriMatcher.match(uri) == QUERY) {
            Cursor cursor = mDatabase.query(SQLiteDbHelper.TABLE_STUDENT, projection, selection, selectionArgs, null,null, sortOrder);
            return cursor;
        }else {
            throw new IllegalAccessError("unKnow Uri,query="+uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert");
        if (mUriMatcher.match(uri) == INSERT) {
            long id = mDatabase.insert(SQLiteDbHelper.TABLE_STUDENT, null, values);
            uri = ContentUris.withAppendedId(URI_CONTENT, id);
            return uri;
        }else {
            throw new IllegalAccessError("unKnow Uri,Insert="+uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete");
        if (mUriMatcher.match(uri) == DELETE) {
            int deleteCount = mDatabase.delete(SQLiteDbHelper.TABLE_STUDENT, selection, selectionArgs);
            return deleteCount;
        }else {
            throw new IllegalAccessError("unKnow Uri,delete="+uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "update");
        if (mUriMatcher.match(uri) == UPDATE) {
            int updateCount = mDatabase.update(SQLiteDbHelper.TABLE_STUDENT, values, selection, selectionArgs);
            return updateCount;
        }else {
            throw new IllegalAccessError("unKnow Uri,update="+uri);
        }
    }

    @Override
    public String getType(Uri arg0) {
        return null;
    }

    private void initProvider() {
        SQLiteDbHelper helper = new SQLiteDbHelper(getContext());
        mDatabase = helper.getWritableDatabase();
    }

}
