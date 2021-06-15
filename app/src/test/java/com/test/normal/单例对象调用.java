package com.test.normal;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * 
 */

/**
 * @brief 
 * @details
 * @author Administrator
 *
 */
public class 单例对象调用 {
	private volatile int i = 0;
	

	public synchronized int getI() {
		return i++;
	}


	@Test
	public void main() {
		// TODO Auto-generated method stub
		/*System.out.println(TestSingleInstance.getInstance());
		System.out.println(TestSingleInstance.getInstance());
		System.out.println(TestSingleInstance.getInstance());*/
		
		new 单例对象.Builder();
		
		final CountDownLatch latch = new CountDownLatch(1);
		int threadCount = 1000;
		for (int i = 0; i < threadCount; i++) {
			new Thread() {
				@Override
				public void run() {
					try {
						latch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(单例对象.getInstance().hashCode());
				}
			}.start();
		}
		latch.countDown();
	}
}
