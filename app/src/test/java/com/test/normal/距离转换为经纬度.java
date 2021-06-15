package com.test.normal; /**
 * 
 */

import org.junit.Test;

/**
 * @brief 
 * @details
 * @author Administrator
 *
 */
public class 距离转换为经纬度 {

	@Test
	public void main() {
		doLatDegress(0.5);
		doLngDegress(0.5, 45.0);
		doLngDegress(0.5, 0.0);
	}
	
	/**
	* 距离转换成经度
	* @param distance
	* @return
	*/
	private static Double doLngDegress(double distance,Double latitude) {
		double lngDegree = 2 * Math.asin(Math.sin((double)distance/12742)/Math.cos(latitude));
		// 转换弧度
		lngDegree = lngDegree * (180/Math.PI);
		System.out.println(lngDegree);
		return lngDegree;
	}
 
/**
	* 距离转换成纬度
	* @param distance
	* @return
	*/
	private static Double doLatDegress(double distance) {
		double latDegrees = (double)distance/6371;
		// 转换弧度
		latDegrees = latDegrees * (180/Math.PI);
		System.out.println(latDegrees);
		return latDegrees;
	}

}
