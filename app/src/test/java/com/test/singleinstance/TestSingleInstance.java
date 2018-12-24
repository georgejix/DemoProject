package com.test.singleinstance; /**
 * 
 */

/**
 * @brief 
 * @details
 * @author jix
 *
 */
public class TestSingleInstance {
	private static volatile TestSingleInstance instance = null;
	
	private TestSingleInstance() {
		
	}
	
	public static TestSingleInstance getInstance() {
		if(null == instance) {
			synchronized (TestSingleInstance.class) {
				if(null == instance) {
					instance = new TestSingleInstance();
				}
			}
		}
		return instance;
	}
	
	static class Builder{}
}
