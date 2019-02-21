package com.test.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;

import java.util.Random;

@ContentView(R.layout.activity_sqlite)
public class SqliteActivity extends Activity {
    private final String TAG = "SqliteActivity";

    @ViewInject(R.id.textview_content)
    private TextView contentTextView;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        initView();
    }

    private void initView(){
        SQLiteDbHelper helper = new SQLiteDbHelper(getApplicationContext());
        database = helper.getWritableDatabase();
    }

    public void add(View view){
        insertStudents();
    }
    public void delete(View view){
        deleteStudents();
    }
    public void alter(View view){
        updateStudents();
    }
    public void query(View view){
        queryStudents();
    }


    private void insertStudents() {
        for (int i = 0; i < 5; i++) {
            ContentValues values = studentToContentValues(mockStudent(i));
            if(null != database) {
                database.insert(SQLiteDbHelper.TABLE_STUDENT, null, values);
            }
        }
    }

    private void deleteStudents() {
        if(null != database) {
            database.delete(SQLiteDbHelper.TABLE_STUDENT,
                    "id = ?", new String[]{"3"});
        }
    }

    private void updateStudents() {
        if(null != database) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", "Jerry");
            database.update(SQLiteDbHelper.TABLE_STUDENT,
                    contentValues, "id = ?", new String[]{"3"});
        }
    }

    private void queryStudents() {
        if(null != database) {
            // 相当于 select * from students 语句
            Cursor cursor = database.query(SQLiteDbHelper.TABLE_STUDENT, null,
                    "id = ?", new String[]{"3"},
                    null, null, null, null);
            if(null != cursor) {
                // 不断移动光标获取值
                while (cursor.moveToNext()) {
                    // 直接通过索引获取字段值
                    int stuId = cursor.getInt(0);

                    // 先获取 name 的索引值，然后再通过索引获取字段值
                    String stuName = cursor.getString(cursor.getColumnIndex("name"));
                    Log.e(TAG, "id: " + stuId + " name: " + stuName);
                }
                // 关闭光标
                cursor.close();
            }
        }
    }

    // 构建 student 对象
    private Student mockStudent(int i) {
        Student student = new Student();
        student.setId(i);
        student.setName("user" + i);
        student.setTel_no(String.valueOf(new Random().nextInt(200000)));
        student.setCls_id(new Random().nextInt(5));
        return student;
    }

    // 将 student 对象的值存储到 ContentValues 中
    private ContentValues studentToContentValues(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", student.getId());
        contentValues.put("name", student.getName());
        contentValues.put("tel_no", student.getTel_no());
        contentValues.put("cls_id", student.getCls_id());
        return contentValues;
    }
}
