package com.hexuan.htmltoword.util;

import java.io.*;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author hexuan.wang
 */
public class Base64 {

    public static void main(String[] args) {
        String strImg = GetImageStr();
        System.out.println(strImg);
        GenerateImage(strImg);
    }

    /**
     * 图片转化成base64字符串
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @return
     */
    public static String GetImageStr() {
        //待处理的图片
        String imgFile = "demo.jpg";
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        //返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    /**
     * base64字符串转化成图片
      * @param imgStr
     * @return
     */
    public static boolean GenerateImage(String imgStr) {  //对字节数组字符串进行Base64解码并生成图片
        //图像数据为空
        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                //调整异常数据
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            //新生成的图片
            String imgFilePath = "/Users/cat/Pictures/" + System.currentTimeMillis() + ".jpg";
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}