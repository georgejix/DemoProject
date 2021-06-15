package com.test.normal;

public enum GoodsType
{
    NORMAL("1"),// 普通商品
    EXPIRY_LIMITED("2"),// 有效期商品
    WEIGHT("3"),// 称重商品
    NOCODE("4"),// 无码商品
    CHUAN_MA("5"),// 串码
    BOOKING("6");// 服务型商品

    private String type;

    GoodsType(String type)
    {
        this.type = type;
    }

    public static GoodsType getType(String type)
    {
        GoodsType goodsType = NORMAL;
        switch (type)
        {
            case "1":
                goodsType = NORMAL;
                break;
            case "2":
                goodsType = EXPIRY_LIMITED;
                break;
            case "3":
                goodsType = WEIGHT;
                break;
            case "4":
                goodsType = NOCODE;
                break;
            case "5":
                goodsType = CHUAN_MA;
                break;
            case "6":
                goodsType = BOOKING;
                break;
        }
        return goodsType;
    }
}
