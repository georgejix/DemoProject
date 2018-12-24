package com.test.singleinstance; /**
 * 
 */

import org.junit.Test;

/**
 * @brief 
 * @details
 * @author Administrator
 *
 */
public class TestByte {

	@Test
	private void 测试比特() {
		int i1 = 103;
		System.out.println(Integer.SIZE);
		System.out.println(i1 & 0x0000000F);
	}

}
