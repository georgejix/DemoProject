package com.test.singleinstance;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

/**
 * 
 */

/**
 * @brief 
 * @details
 * @author Administrator
 *
 */
public class Gson测试 {
	@Test
	public void main() {
		Gson测试 testGson = new Gson测试();
		//testGson.t1();
		//testGson.t2();
		testGson.t3();
		String content = null;
		try {
			Gson gson = new Gson();
			ABC abc = new ABC();
			content = gson.toJson(abc);
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	class ABC{
		String a;
		String b;

		public String getB() {
			return "222";
		}

		public void setB(String b) {
			this.b = b;
		}
	}
	
	private void t1() {
		/*String s = "{\"deviceCode\":\"CAM-01421CFF180000FF\",\"deviceName\":\"ZS-C1003907\",\"deviceState\":\"离线\",\"machineType1\":\"\",\"machineType2\":\"\",\"machineType3\":\"\",\"machineModule\":\"\",\"railState\":\"\",\"location\":{\"lon\":null,\"lat\":null}}";
		Gson gson = new Gson();
		Gson测试类1 j = gson.fromJson(s, Gson测试类1.class);
		System.out.println(j.getLocation().getLat());*/
		String s = "{\"fuelLevel\":\"12\",\"engineSpeed\":\"12\",\"fuelTemperature\":\"12\",\"waterTemperature\":\"12\",\"workTime\":{\"time\":\"23400\",\"effectTime\":\"21600\",\"lazyTime\":\"1800\",\"detail\":[{\"start\":\"1528236366\",\"end\":\"1528239966\",\"isEffective\":1},{\"start\":\"1528240566\",\"end\":\"1528241166\",\"isEffective\":0},{\"start\":\"1528241166\",\"end\":\"1528248366\",\"isEffective\":1},{\"start\":\"1528248366\",\"end\":\"1528248966\",\"isEffective\":0},{\"start\":\"1528284966\",\"end\":\"1528288566\",\"isEffective\":1},{\"start\":\"1528288566\",\"end\":\"1528289166\",\"isEffective\":0},{\"start\":\"1528289166\",\"end\":\"1528296366\",\"isEffective\":1}]},\"fuelConsume\":{\"total\":\"12.58\",\"work\":\"10.76\",\"lazy\":\"1.82\",\"detail\":[{\"start\":\"1528236366\",\"end\":\"1528239966\",\"consume\":\"2.67\",\"isEffective\":1},{\"start\":\"1528240566\",\"end\":\"1528241166\",\"consume\":\"0.63\",\"isEffective\":0},{\"start\":\"1528241166\",\"end\":\"1528248366\",\"consume\":\"2.72\",\"isEffective\":1},{\"start\":\"1528248366\",\"end\":\"1528248966\",\"consume\":\"0.64\",\"isEffective\":0},{\"start\":\"1528284966\",\"end\":\"1528288566\",\"consume\":\"2.53\",\"isEffective\":1},{\"start\":\"1528288566\",\"end\":\"1528289166\",\"consume\":\"0.55\",\"isEffective\":0},{\"start\":\"1528289166\",\"end\":\"1528296366\",\"consume\":\"2.84\",\"isEffective\":1}]}}";
		Gson gson = new Gson();
		Gson测试类2 j = gson.fromJson(s, Gson测试类2.class);
		Gson测试类2.Detail d = j.getWorkTime().getDetail().get(0);
		j.getWorkTime().getDetail().clear();
		j.getWorkTime().getDetail().add(d);
		System.out.println(j.getWorkTime().getDetail().get(0).getEnd());
	}
	
	private void t2() {
		Gson gson = new Gson();
		Type dataListType = new TypeToken<ArrayList<两个接口函数重名的实现>>() {}.getType();
		System.out.println(dataListType.toString());
        List<两个接口函数重名的实现> dataList = gson.fromJson("[\"513-3-4\",\"1541-7-8\",\"4105-17-18\",\"78385-19-52\",\"79413-23-65\",\"148290-4-69\",\"149318-1-66\",\"148547-5-70\",\"147783-2-67\",\"148804-6-71\",\"148033-3-68\",\"149061-7-65\",\"143930-1-127\",\"\"]", dataListType);
        System.out.println(dataList.get(0));
	}
	
	private void t3() {
		String s = "{\"fuelLevel\":null,\"engineSpeed\":null,\"fuelTemperature\":null,\"waterTemperature\":null,\"workTime\":{\"time\":6120,\"effectTime\":4380,\"lazyTime\":1740,\"detail\":[{\"end\":40500,\"isEffective\":1,\"start\":34200}]},\"fuelConsume\":{\"total\":19.25,\"work\":17.3,\"lazy\":1.95,\"average\":11.32,\"detail\":[]}}";
		Gson gson = new Gson();
		try {
			JsonObject jsonObject = new JsonObject();
			jsonObject = gson.fromJson(s, JsonObject.class);
			System.out.println(jsonObject.get("workTime"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
