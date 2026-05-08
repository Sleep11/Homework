package com.bwie.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 郜怀达
 * @version 1.0
 * @description: MD5 加密工具类，只能加密，不能解密，网上的MD5解密是有一个资源库在支撑着
 * @date 2022/9/16 23:04
 */
public class MD5Utils {

    /**
    * @description: MD5对字符串进行加密的工具类
    * @param: strSrc         
    * @return: java.lang.String
    * @author: 郜怀达
    * @date: 2022/9/16 23:07
    */
    public static String encrypt(String strSrc) {
        try {
            char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            byte[] bytes = strSrc.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错！！+" + e);
        }
    }
}
