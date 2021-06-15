package com.test.normal;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;


/**
 * Created by jix on 2019/1/25.
 */

public class 二进制与字符串互转 {
    @Test
    public void main(){
        字符串转二进制("测试一个字符串！");
        整型转二进制(11);
        整型10进制转16进制(555);

        byte intbyte[] = int2ByteArray(-28);
        for(byte b : intbyte){
            System.out.print(b + " ");
        }
        System.out.println("\n");
        for(byte b : intbyte){
            System.out.print(byte转二进制字符串(b) + " ");
        }
        System.out.println("\n");
        System.out.println(bytesToHexString(intbyte));

    }

    private void 字符串转二进制(String string){
        byte bytes[] = new byte[0];
        try {
            bytes = string.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(bytes.length + "");
        for(byte b : bytes) {
            System.out.print(b + " ");
        }
        System.out.println("");
        二进制转字符串(bytes);
    }

    private void 二进制转字符串(byte b[]){
        String string = "";
        try {
            string = new String(b, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(string);
    }

    private void 整型转二进制(int number){
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < 32; i++){
            sBuilder.append(number & 1);
            number = number >>> 1;
        }
        System.out.println(sBuilder.reverse().toString());
    }

    private String byte转二进制字符串(byte b){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < 8; i++){
            sb.append(b & 0x01);
            b = (byte) (b >>> 1);
        }
        return sb.reverse().toString();
    }

    private void 整型10进制转16进制(int number){
        String hex = Integer.toHexString(number);
        System.out.println(hex);
        System.out.println(Integer.parseInt(hex, 16) + "");
    }

    private void 长整型10进制转16进制(long number){
        String lhex = Long.toHexString(number);
        System.out.println(lhex);
        System.out.println(Integer.parseInt(lhex, 16) + "");
    }

     /**
       * int转byte[]
       * 该方法将一个int类型的数据转换为byte[]形式，因为int为32bit，而byte为8bit所以在进行类型转换时，知会获取低8位，
       * 丢弃高24位。通过位移的方式，将32bit的数据转换成4个8bit的数据。注意 &0xff，在这当中，&0xff简单理解为一把剪刀，
       * 将想要获取的8位数据截取出来。
       * @param i 一个int数字
       * @return byte[]
       */
    public byte[] int2ByteArray(int i){
        byte[] result=new byte[4];
        result[0]=(byte)((i >> 24)& 0xFF);
        result[1]=(byte)((i >> 16)& 0xFF);
        result[2]=(byte)((i >> 8)& 0xFF);
        result[3]=(byte)(i & 0xFF);
        return result;
    }
     /**
       * byte[]转int
       * 利用int2ByteArray方法，将一个int转为byte[]，但在解析时，需要将数据还原。同样使用移位的方式，将适当的位数进行还原，
       * 0xFF为16进制的数据，所以在其后每加上一位，就相当于二进制加上4位。同时，使用|=号拼接数据，将其还原成最终的int数据
       * @param bytes byte类型数组
       * @return int数字
       */
     public int bytes2Int(byte[] bytes){
         int num=bytes[3] & 0xFF;
         num |=((bytes[2] <<8)& 0xFF00);
         num |=((bytes[1] <<16)& 0xFF0000);
         num |=((bytes[0] <<24)& 0xFF0000);
         /*int num=bytes[3];
         num |=((bytes[2] <<8));
         num |=((bytes[1] <<16));
         num |=((bytes[0] <<24));*/
         return num;
     }

    public String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
