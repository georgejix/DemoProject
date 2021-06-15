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
public class 地图测试 {

	@Test
	public void main() {
		// TODO Auto-generated method stub
		地图测试 测试 = new 地图测试();
		//118.79523,32.061075
		//118.796877,32.060321
		double 经度 = 118.9323234558d;
		double 纬度 = 32.0582818753d;
		double 坐标[] = 测试.gaoDeToBaidu(经度, 纬度);
		System.out.println(坐标[0] + "," + 坐标[1]);
	}
	
	private double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
	    double[] bd_lat_lon = new double[2];
	    double PI = 3.14159265358979324 * 3000.0 / 180.0;
	    double x = gd_lon, y = gd_lat;
	    double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
	    double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
	    bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
	    bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
	    return bd_lat_lon;
	}
	

	private double[] bdToGaoDe(double bd_lat, double bd_lon) {
	    double[] gd_lat_lon = new double[2];
	    double PI = 3.14159265358979324 * 3000.0 / 180.0;
	    double x = bd_lon - 0.0065, y = bd_lat - 0.006;
	    double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
	    double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
	    gd_lat_lon[0] = z * Math.cos(theta);
	    gd_lat_lon[1] = z * Math.sin(theta);
	    return gd_lat_lon;
	 }

}
