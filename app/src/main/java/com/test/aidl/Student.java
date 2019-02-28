package com.test.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by jix on 2019/2/28.
 */

public class Student implements Parcelable {
    private final String TAG = "Student";

    private int id;
    private String name;
    private String sex;
    private User user;

    public Student(int id, String name, String sex, User user){
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.user = user;
    }

    protected Student(Parcel in) {
        id = in.readInt();
        name = in.readString();
        sex = in.readString();
        user = (User) in.readSerializable();
        //in.readParcelable(Thread.currentThread().getContextClassLoader());
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(sex);
        parcel.writeSerializable(user);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                (null == user ? "" : (
                ", user=" + user.getName() +
                "," + user.getAge() +
                "," + user.getSex())) +
                '}';
    }
}
