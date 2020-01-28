package com.test.aidl;


import java.io.Serializable;

/**
 * Created by jix on 2019/2/28.
 */

public class User implements Serializable {
    //反序列化时，如果这个字段不相同，会抛出：
    // java.io.InvalidClassException: com.test.aidl.User; local class incompatible: stream classdesc serialVersionUID = 1001, local class serialVersionUID = 1002
    private static final long serialVersionUID = 1001L;
    private String name;
    private String age;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
