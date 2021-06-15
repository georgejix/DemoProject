package com.test.normal;

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
public class Gson测试
{
    @Test
    public void main()
    {
        Gson测试 testGson = new Gson测试();
        //testGson.t1();
        //testGson.t2();
        testGson.t3();
        String content = null;
        try
        {
            Gson gson = new Gson();
            ABC abc = new ABC();
            content = gson.toJson(abc);
            System.out.println(content);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        testGson.t4();
        testGson.t5();
    }

    private void t5()
    {
        String s = "{\"balancePayInfo\":{\"receipts\":\"0\",\"receiptsCount\":\"0\",\"refundCount\":\"0\",\"refundValue\":\"0\",\"tagName\":\"余额\",\"value\":\"0\"},\"payDetail\":[{\"isCustomPay\":false,\"receipts\":\"0\",\"receiptsCount\":\"0\",\"refundCount\":\"0\",\"refundValue\":\"0\",\"tagName\":\"支付宝\",\"topUpCount\":\"0\",\"topUpMoney\":\"0\",\"value\":\"0\"},{\"isCustomPay\":false,\"receipts\":\"0\",\"receiptsCount\":\"0\",\"refundCount\":\"0\",\"refundValue\":\"0\",\"tagName\":\"微信\",\"topUpCount\":\"0\",\"topUpMoney\":\"0\",\"value\":\"0\"},{\"isCustomPay\":false,\"receipts\":\"0\",\"receiptsCount\":\"0\",\"refundCount\":\"0\",\"refundValue\":\"0\",\"tagName\":\"云闪付\",\"topUpCount\":\"0\",\"topUpMoney\":\"0\",\"value\":\"0\"},{\"isCustomPay\":false,\"receipts\":\"5\",\"receiptsCount\":\"1\",\"refundCount\":\"0\",\"refundValue\":\"0\",\"tagName\":\"现金\",\"topUpCount\":\"0\",\"topUpMoney\":\"0\",\"value\":\"5\"}],\"totalInfo\":{\"topUpAmount\":\"0\",\"topupOrderCount\":\"0\",\"totalBalance\":\"5\",\"totalOrderCount\":\"1\",\"totalReceipts\":\"5\",\"totalReceiptsCount\":\"1\",\"totalRefundAmount\":\"0\",\"totalRefundCount\":\"0\",\"totalReturnOrderCount\":\"0\",\"totalTradeCount\":\"1\"},\"aliPayBalance\":0,\"aliPayReceipts\":0,\"aliPayRefundAmount\":0,\"balancePayBalance\":0,\"balancePayReceipts\":0,\"balancePayRefundAmount\":0,\"cashBalance\":0,\"cashReceipts\":0,\"cashRefundAmount\":0,\"cloudPayBalance\":0,\"cloudPayReceipts\":0,\"cloudPayRefundAmount\":0,\"customPayBalance\":0,\"customPayReceipts\":0,\"customPayRefundAmount\":0,\"endTime\":\"2020-09-22 20:16:36\",\"startTime\":\"2020-09-22 00:00:00\",\"topUpAmount\":0,\"topupOrderCount\":0,\"totalBalance\":0,\"totalOrderCount\":0,\"totalReceipts\":5,\"totalRefundAmount\":0,\"totalReturnOrderCount\":0,\"wechatPayBalance\":0,\"wechatPayReceipts\":0,\"wechatPayRefundAmount\":0}";
        Gson gson = new Gson();
        JsonObject j = gson.fromJson(s, JsonObject.class);
        System.out.println(j.get("balancePayInfo").getAsJsonObject().get("receipts"));

    }

    private void t4()
    {
        Gson gson = new Gson();
        List list = new ArrayList();
        System.out.println("----" + gson.toJson(list));
    }

    class ABC
    {
        String a;

        String b;

        public String getB()
        {
            return "222";
        }

        public void setB(String b)
        {
            this.b = b;
        }
    }

    private void t1()
    {
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

    private void t2()
    {
        Gson gson = new Gson();
        Type dataListType = new TypeToken<ArrayList<两个接口函数重名的实现>>()
        {
        }.getType();
        System.out.println(dataListType.toString());
        List<两个接口函数重名的实现> dataList = gson.fromJson("[\"513-3-4\",\"1541-7-8\",\"4105-17-18\",\"78385-19-52\",\"79413-23-65\",\"148290-4-69\",\"149318-1-66\",\"148547-5-70\",\"147783-2-67\",\"148804-6-71\",\"148033-3-68\",\"149061-7-65\",\"143930-1-127\",\"\"]", dataListType);
        System.out.println(dataList.get(0));
    }

    private void t3()
    {
        String s = "{\"fuelLevel\":null,\"engineSpeed\":null,\"fuelTemperature\":null,\"waterTemperature\":null,\"workTime\":{\"time\":6120,\"effectTime\":4380,\"lazyTime\":1740,\"detail\":[{\"end\":40500,\"isEffective\":1,\"start\":34200}]},\"fuelConsume\":{\"total\":19.25,\"work\":17.3,\"lazy\":1.95,\"average\":11.32,\"detail\":[]}}";
        Gson gson = new Gson();
        try
        {
            JsonObject jsonObject = new JsonObject();
            jsonObject = gson.fromJson(s, JsonObject.class);
            System.out.println(jsonObject.get("workTime"));
        } catch (Exception e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }
}
