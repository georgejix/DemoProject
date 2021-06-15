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
public class 将list数据按比例缩减长度 {
	private List<String> dataList = new ArrayList<String>();
	private List<String> dataList100 = new ArrayList<String>();

	@Test
	public void main() {
		将list数据按比例缩减长度 testFilter = new 将list数据按比例缩减长度();
		testFilter.t1();
	}
	
	private void t1() {
		for(int i = 0; i < 130; i++) {
			dataList.add(i + "");
		}
		System.out.println("dataList:");
		for(String s : dataList) {
			System.out.print(s + " ");
		}
		if(null != dataList) {
			if(dataList.size() > 100) {
				int dataListSize = dataList.size();
				double gap = 100.0 / dataListSize;
				int index = 1;
				double choose = 0;
				for(String s : dataList) {
					choose += gap;
					if(choose > index) {
						dataList100.add(s);
						index++;
					}
				}
			}else {
				dataList100.addAll(dataList);
			}
		}
		System.out.println("dataList100:");
		for(String s : dataList100) {
			System.out.print(s + " ");
		}
		System.out.println("\ndataList100.size:" + dataList100.size());
	}
}
