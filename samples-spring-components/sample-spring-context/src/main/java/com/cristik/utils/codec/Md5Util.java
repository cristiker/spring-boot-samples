/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.cristik.utils.codec;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * MD5不可逆加密工具类
 *
 * @author ThinkGem
 */
public class Md5Util {

    private static final String MD5 = "MD5";
    private static final String DEFAULT_ENCODING = "UTF-8";


    /**
     * 对输入字符串进行md5散列.
     *
     * @param input 加密字符串
     */
    public static String md5(String input) {
        return md5(input, 1);
    }

    /**
     * 对输入字符串进行md5散列.
     *
     * @param input      加密字符串
     * @param iterations 迭代次数
     */
    public static String md5(String input, int iterations) {
        try {
            return EncodeUtil.encodeHex(DigestUtil.digest(input.getBytes(DEFAULT_ENCODING), MD5, null, iterations));
        } catch (UnsupportedEncodingException e) {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 对输入字符串进行md5散列.
     *
     * @param input 加密字符串
     */
    public static byte[] md5(byte[] input) {
        return md5(input, 1);
    }

    /**
     * 对输入字符串进行md5散列.
     *
     * @param input      加密字符串
     * @param iterations 迭代次数
     */
    public static byte[] md5(byte[] input, int iterations) {
        return DigestUtil.digest(input, MD5, null, iterations);
    }

    /**
     * 对文件进行md5散列.
     */
    public static byte[] md5(InputStream input) throws IOException {
        return DigestUtil.digest(input, MD5);
    }

}
