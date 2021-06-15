package com.test.normal;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @brief 
 * @details
 * @author Administrator
 *
 */
public class 同步锁 {
	private Object lockObject = new Object();
	List<Integer> list = new ArrayList<Integer>();

	@Test
	public void main() {
		同步锁 testList = new 同步锁();
		testList.test1();
	}
	
	private void test1() {
		for(int i = 0; i < 100; i++) {
			list.add(i);
		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1);
					System.out.println("before clear");
					synchronized (lockObject) {
						list.clear();
						System.out.println("after clear");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
                    System.out.println("" + e);
					e.printStackTrace();
				}
			}
		}).start();
        /*Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()){
            Integer integer = iterator.next();
            if(integer==2)
                list.remove(integer);
        }*/
		synchronized (lockObject) {
	        for(int i : list) {
	        	System.out.println(i + "");
	        	//list.remove(0);
	        }
		}
	}
}
