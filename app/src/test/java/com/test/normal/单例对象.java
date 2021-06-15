package com.test.normal;

/**
 * Created by Administrator on 2019/1/25.
 */

public class 单例对象 {
    private static volatile 单例对象 instance = null;

    private 单例对象() {

    }

    public static 单例对象 getInstance() {
        if(null == instance) {
            synchronized (单例对象.class) {
                if(null == instance) {
                    instance = new 单例对象();
                }
            }
        }
        return instance;
    }

    static class Builder{}
}

