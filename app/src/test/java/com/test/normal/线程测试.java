package com.test.normal;

import org.junit.Test;

public class 线程测试 {
    private Thread thread1;
    private Runnable runnable1;

    @Test
    public void test(){
        t1();
    }

    private void t1(){
        thread1 = new MyThread1();
        thread1.run();
    }

    class MyThread1 extends Thread{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().hashCode() + "");
        }
    }
}
