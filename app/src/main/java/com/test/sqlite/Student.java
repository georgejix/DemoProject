package com.test.sqlite;

/**
 * Created by jix on 2019/2/21.
 */

public class Student {
    private long id;
    private String name;
    private String tel_no;
    private int cls_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel_no() {
        return tel_no;
    }

    public void setTel_no(String tel_no) {
        this.tel_no = tel_no;
    }

    public int getCls_id() {
        return cls_id;
    }

    public void setCls_id(int cls_id) {
        this.cls_id = cls_id;
    }
}
