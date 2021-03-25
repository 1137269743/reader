package com.reader.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author Flagship
 * @Date 2021/3/20 23:26
 * @Description
 */
public class Md5Utils {
    /**
     * 对字符串进行加密
     * @param source 要加密的字符串
     * @return 加密后的密文
     */
    public static String md5Digest(String source) {
        return DigestUtils.md5Hex(source);
    }

    /**
     * 对字符串进行加盐加密
     * @param source 要加密的字符串
     * @param salt 盐值
     * @return 加密后的密文
     */
    public static String md5Digest(String source, Integer salt) {
        char[] ca = source.toCharArray();
        //对字符串进行移位操作
        for (int i = 0; i < ca.length; i++) {
            ca[i] = (char)(ca[i] + salt);
        }
        String target = new String(ca);
        String md5 = DigestUtils.md5Hex(target);
        return md5;
    }

    /**
     * 生成随机（0-255）的数字，作为盐值
     * @return 盐值
     */
    public static int createSalt() {
        return (int) (Math.random() * 255);
    }
}
