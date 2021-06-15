package com.test.normal; /**
 * 
 */

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @brief 
 * @details
 * @author jix
 *
 */
public class Gson测试类2 {
	private String fuelLevel;
	private String engineSpeed;
	private String fuelTemperature;
	private String waterTemperature;
	private WorkTime workTime;
	private FuelConsume fuelConsume;
	
	
	
	public String getFuelLevel() {
		return fuelLevel;
	}

	public void setFuelLevel(String fuelLevel) {
		this.fuelLevel = fuelLevel;
	}

	public String getEngineSpeed() {
		return engineSpeed;
	}

	public void setEngineSpeed(String engineSpeed) {
		this.engineSpeed = engineSpeed;
	}

	public String getFuelTemperature() {
		return fuelTemperature;
	}

	public void setFuelTemperature(String fuelTemperature) {
		this.fuelTemperature = fuelTemperature;
	}

	public String getWaterTemperature() {
		return waterTemperature;
	}

	public void setWaterTemperature(String waterTemperature) {
		this.waterTemperature = waterTemperature;
	}

	public WorkTime getWorkTime() {
		return workTime;
	}

	public void setWorkTime(WorkTime workTime) {
		this.workTime = workTime;
	}
	
	public FuelConsume getFuelConsume() {
		return fuelConsume;
	}

	public void setFuelConsume(FuelConsume fuelConsume) {
		this.fuelConsume = fuelConsume;
	}



	class WorkTime{
		private String time;
		private String effectTime;
		private String lazyTime;
		private List<Detail> detail;
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getEffectTime() {
			return effectTime;
		}
		public void setEffectTime(String effectTime) {
			this.effectTime = effectTime;
		}
		public String getLazyTime() {
			return lazyTime;
		}
		public void setLazyTime(String lazyTime) {
			this.lazyTime = lazyTime;
		}
		public List<Detail> getDetail() {
			Collections.sort(detail, new WorkTimeComparator());
			return detail;
		}
		public void setDetail(List<Detail> detail) {
			this.detail = detail;
		}
		
	}
	
	public class Detail{
		private String start;
		private String end;
		private String isEffective;
		public String getStart() {
			return start;
		}
		public void setStart(String start) {
			this.start = start;
		}
		public String getEnd() {
			return end;
		}
		public void setEnd(String end) {
			this.end = end;
		}
		public String getIsEffective() {
			return isEffective;
		}
		public void setIsEffective(String isEffective) {
			this.isEffective = isEffective;
		}
		
	}
	
	class FuelConsume{
		private String total;
		private String work;
		private String lazy;
		private List<FuelConsumeDetail> detail;
		public String getTotal() {
			return total;
		}
		public void setTotal(String total) {
			this.total = total;
		}
		public String getWork() {
			return work;
		}
		public void setWork(String work) {
			this.work = work;
		}
		public String getLazy() {
			return lazy;
		}
		public void setLazy(String lazy) {
			this.lazy = lazy;
		}
		public List<FuelConsumeDetail> getDetail() {
			Collections.sort(detail, new FuelConsumeComparator());
			return detail;
		}
		public void setDetail(List<FuelConsumeDetail> detail) {
			this.detail = detail;
		}
		
	}
	
	class FuelConsumeDetail{
		private String start;
		private String end;
		private String consume;
		private String isEffective;
		public String getStart() {
			return start;
		}
		public void setStart(String start) {
			this.start = start;
		}
		public String getEnd() {
			return end;
		}
		public void setEnd(String end) {
			this.end = end;
		}
		public String getConsume() {
			return consume;
		}
		public void setConsume(String consume) {
			this.consume = consume;
		}
		public String getIsEffective() {
			return isEffective;
		}
		public void setIsEffective(String isEffective) {
			this.isEffective = isEffective;
		}
		
	}
	
	class WorkTimeComparator implements Comparator{

		@Override
		public int compare(Object o1, Object o2) {
			// TODO Auto-generated method stub
			Detail detail1 = (Detail) o1;
			Detail detail2 = (Detail) o2;
			try {
				return Long.parseLong(detail1.getStart()) < Long.parseLong(detail2.getStart()) ? -1 : 1;
			}catch (Exception e) {
				// TODO: handle exception
			}
			return 0;
		}
		
	}
	
	class FuelConsumeComparator implements Comparator{

		@Override
		public int compare(Object o1, Object o2) {
			// TODO Auto-generated method stub
			FuelConsumeDetail detail1 = (FuelConsumeDetail) o1;
			FuelConsumeDetail detail2 = (FuelConsumeDetail) o2;
			try {
				return Long.parseLong(detail1.getStart()) < Long.parseLong(detail2.getStart()) ? -1 : 1;
			}catch (Exception e) {
				// TODO: handle exception
			}
			return 0;
		}
		
	}
}
