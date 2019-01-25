package com.test.singleinstance;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 
 */

/**
 * @brief 
 * @details
 * @author Administrator
 *
 */
public class 日期格式化 {

	@Test
	public void main() {
		// TODO Auto-generated method stub
		//日期格式化 t = new 日期格式化();
		//t.t1();
		//t.t2();
		//t.t3();
		System.out.println(getSeconds("2019-01-09 01:05:53"));
	}


	private long getSeconds(String time) {
		long seconds = 0;
		try {
			time = time.substring(time.indexOf(" ") + 1);
			String times[] = time.split(":");
			seconds = Long.parseLong(times[0]) * 60 * 60 + Long.parseLong(times[1]) * 60 + Long.parseLong(times[2]);
		}catch (Exception e){}
		return seconds;
	}
	
	private void t1() {
SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			System.out.println(sdfOne.parse(sdfOne.format(System.currentTimeMillis())).getTime() + "");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void t2() {
		SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long today = sdfOne.parse(sdfOne.format(System.currentTimeMillis())).getTime() / 1000;
			System.out.println(sdfOne.format(today * 1000) + "▼");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void t3() {
		SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfOne_ = new SimpleDateFormat("yyyy年MM月dd日");
		try {
			long today = sdfOne.parse("2018-08-23").getTime() / 1000;
			System.out.println(sdfOne_.format(today * 1000) + "▼");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
