package com.jaelyn.chat.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jingling.zhang@ucarinc.com
 * @description TODO
 * @date 2018/12/19 13:47
 **/
public class MD5Util {
    private static Logger LOGGER = LoggerFactory.getLogger(MD5Util.class);

    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String getMD5Str(String str) throws Exception {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            LOGGER.error("MD5加密出现错误，" + e.toString());
            throw new Exception("MD5加密出现错误，" + e.toString());
        }
    }


    public static void main(String args[]) {
        try {
            System.out.println(MD5Util.getMD5Str("12345"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
