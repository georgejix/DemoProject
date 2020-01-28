package com.test.thread;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.mplanet.testhandler.R;

import java.util.concurrent.CountDownLatch;


/**
 * thread和runnable对象，主线程直接调用run()方法，线程仍在主线程，只有调用thread.start()，才会到子线程
 * 即，只有thread.start()会另起子线程，run()后，程序仍在调用线程，不会另外开辟线程
 *
 *
 * 为什么要区分进入阻塞状态和和非阻塞状态两种情况了，是因为当阻塞状态时，如果有interrupt()发生，
 * 系统除了会抛出InterruptedException异常外，还会调用interrupted()函数，调用时能获取到中断状态是true的状态，
 * 调用完之后会复位中断状态为false，所以异常抛出之后通过isInterrupted()是获取不到中断状态是true的状态，
 * 从而不能退出循环，因此在线程未进入阻塞的代码段时是可以通过isInterrupted()来判断中断是否发生来控制循环，
 * 在进入阻塞状态后要通过捕获异常来退出循环。因此使用interrupt()来退出线程的最好的方式应该是两种情况都要考虑
 */
@ContentView(R.layout.activity_thread)
public class ThreadActivity extends Activity {
    private final String TAG = "ThreadActivity";

    private MyThread thread1, thread2;
    private MyRunnable myRunnable1;
    private Thread thread3;
    private CountDownLatch countDownLatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        //test1();
        //test2();
        //test3();
        //test4();
        test5();
    }

    private void test5(){
        final Thread2 thread1 = new Thread2();
        thread1.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                thread1.interrupt();
            }
        }).start();
    }

    class Thread2 extends Thread{
        @Override
        public void run() {
            super.run();
            while (!isInterrupted()){
                Log.d(TAG, "Thread2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    private void test4(){
        //wait notify
        final Object lock = new Object();
        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    Log.d(TAG, "thread1 in");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "thread1 out");
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    Log.d(TAG, "thread2 in , thread1.state=" + thread1.getState().toString());
                    lock.notifyAll();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "thread2 out");

                }
            }
        });
        thread1.start();
        thread2.start();
    }

    private void test3(){
        //test blocked
        final Object lock = new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    while(true){

                    }
                }
            }
        }).start();
        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    Log.d(TAG, "1111111111");
                }
            }
        });
        thread1.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, thread1.getState().toString());
            }
        }).start();
    }

    private void test2(){
        countDownLatch = new CountDownLatch(1);
        thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                    Log.d(TAG, "wait for countdownlatch");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Log.d(TAG, thread3.getState().toString());
        thread3.start();
        Log.d(TAG, thread3.getState().toString());
        countDownLatch.countDown();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, thread3.getState().toString());
            }
        }).start();
    }

    private void test1(){
        Log.d(TAG, "MainThread:" + Thread.currentThread().hashCode());
        thread1 = new MyThread();
        thread1.start();

        thread2 = new MyThread();
        thread2.run();

        myRunnable1 = new MyRunnable();
        myRunnable1.run();
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            Log.d(TAG, "MyThread:" + Thread.currentThread().hashCode());
            new Thread__().run();
        }
    }

    class Thread__ extends Thread{
        @Override
        public void run() {
            Log.d(TAG, "Thread__:" + Thread.currentThread().hashCode());
        }
    }

    class MyRunnable implements Runnable {

        @Override
        public void run() {
            Log.d(TAG, "MyRunnable:" + Thread.currentThread().hashCode());
        }
    }
}
