package com.test.normal; /**
 * 
 */


/**
 * @brief 
 * @details
 * @author jix
 *
 */
public class Gson测试类1 {
	private String deviceCode = "";
	private String deviceName = "";
	private String deviceState = "";
	private String machineType1 = "";
	private String machineType2 = "";
	private String machineType3 = "";
	private String machineModule = "";
	private String railState = "";
	private LatLon location;
	/**
	 * @return the deviceCode
	 */
	public String getDeviceCode() {
		return deviceCode;
	}
	/**
	 * @param deviceCode the deviceCode to set
	 */
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}
	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	/**
	 * @return the deviceState
	 */
	public String getDeviceState() {
		return deviceState;
	}
	/**
	 * @param deviceState the deviceState to set
	 */
	public void setDeviceState(String deviceState) {
		this.deviceState = deviceState;
	}
	/**
	 * @return the machineType1
	 */
	public String getMachineType1() {
		return machineType1;
	}
	/**
	 * @param machineType1 the machineType1 to set
	 */
	public void setMachineType1(String machineType1) {
		this.machineType1 = machineType1;
	}
	/**
	 * @return the machineType2
	 */
	public String getMachineType2() {
		return machineType2;
	}
	/**
	 * @param machineType2 the machineType2 to set
	 */
	public void setMachineType2(String machineType2) {
		this.machineType2 = machineType2;
	}
	/**
	 * @return the machineType3
	 */
	public String getMachineType3() {
		return machineType3;
	}
	/**
	 * @param machineType3 the machineType3 to set
	 */
	public void setMachineType3(String machineType3) {
		this.machineType3 = machineType3;
	}
	/**
	 * @return the machineModule
	 */
	public String getMachineModule() {
		return machineModule;
	}
	/**
	 * @param machineModule the machineModule to set
	 */
	public void setMachineModule(String machineModule) {
		this.machineModule = machineModule;
	}
	/**
	 * @return the railState
	 */
	public String getRailState() {
		return railState;
	}
	/**
	 * @param railState the railState to set
	 */
	public void setRailState(String railState) {
		this.railState = railState;
	}
	
	/**
	 * @return the location
	 */
	public LatLon getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(LatLon location) {
		this.location = location;
	}

	class LatLon{
		private String lon = "";
		private String lat = "";
		/**
		 * @return the lon
		 */
		public String getLon() {
			return lon;
		}
		/**
		 * @param lon the lon to set
		 */
		public void setLon(String lon) {
			this.lon = lon;
		}
		/**
		 * @return the lat
		 */
		public String getLat() {
			return lat;
		}
		/**
		 * @param lat the lat to set
		 */
		public void setLat(String lat) {
			this.lat = lat;
		}
		
	}

	
}
