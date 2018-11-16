package com.cristik.common.utils;

import com.cristik.common.codec.EncodeUtil;
import com.cristik.common.lang.StringUtil;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * @author cristik
 */
public class IdGenUtil {

    private static SecureRandom random = new SecureRandom();

    public static String getId(){
        return UUID.randomUUID().toString();
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    /**
     * 基于Base62编码的SecureRandom随机生成bytes.
     */
    public static String randomBase62(int length) {
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return EncodeUtil.encodeBase62(randomBytes);
    }

    public static String genId(String prefix){
        if (!StringUtil.isNotBlank(prefix)) {
            prefix = "";
        }
        return prefix + (System.currentTimeMillis() + "").substring(1) +
                (System.nanoTime() + "").substring(7, 10);
    }

    public static void main(String[] args) {
        System.out.println(genId(null));
    }
}
