package com.test.normal;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 
 */

/**
 * @brief 
 * @details
 * @author Administrator
 *
 */
public class 测试socket {
	@Test
	public void main() {
		测试socket socketTest = new 测试socket();
		socketTest.test1();
	}
	
	private void test1() {
		try {
			ServerSocket serverSocket = new ServerSocket(0);
			System.out.println(serverSocket.getLocalPort() + "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
