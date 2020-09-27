/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.cristik.common.codec;

import com.cristik.common.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES加密解密工具类
 *
 * @author ThinkGem
 */
public class AesUtil {

    private static final Logger logger = LoggerFactory.getLogger(AesUtil.class);
    private static final String AES = "AES";
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";
    /**
     * 生成AES密钥, 默认长度为128位(16字节).
     */
    private static final int AES_DEFAULT_KEYSIZE = 128;
    /**
     * 生成随机向量, 默认大小为cipher.getBlockSize(), 16字节
     */
    private static final int DEFAULT_IVSIZE = 16;
    /**
     * 用于 生成 generateIV随机数对象
     */
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final byte[] DEFAULT_KEY = new byte[]{-97, 88, -94, 9, 70, -76, 126, 25, 0, 3, -20, 113, 108, 28, 69, 125};

    public static byte[] encode(String content, AesModel mode, AesKeySize aesKeySize, Padding padding, String key
            , String iv, String charSet, int cipherModel) throws NoSuchPaddingException, NoSuchAlgorithmException
            , UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(aesKeySize.size, new SecureRandom(key.getBytes()));

        SecretKey secretKey = keyGenerator.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();

        SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, AES);
        String cipherType = AES + "/" + mode + "/" + padding;
        Cipher cipher = Cipher.getInstance(cipherType);
        cipher.init(cipherModel, keySpec);
        return cipher.doFinal(content.getBytes(charSet));
    }

    /**
     * AES加密
     *
     * @param content 需要加密的内容
     * @return
     */
    public static String encryptAES(String content) {
        try {
            SecretKeySpec keySpec = genSecretKeySpec(AES_DEFAULT_KEYSIZE, null);
            Cipher cipher = Cipher.getInstance(AES);
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] result = cipher.doFinal(byteContent);
            return EncodeUtil.encodeHex(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param content 待解密内容
     * @return
     */
    public static String decryptAES(String content) {
        try {
            SecretKeySpec keySpec = genSecretKeySpec(null, null);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] result = cipher.doFinal(EncodeUtil.decodeHex(content));
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES加密
     *
     * @param content 需要加密的内容
     * @param key     加密密钥
     * @return
     */
    public static String encryptAES(String content, String key) {
        try {
            SecretKeySpec keySpec = genSecretKeySpec(null, key);
            Cipher cipher = Cipher.getInstance(AES);
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] result = cipher.doFinal(byteContent);
            return EncodeUtil.encodeHex(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param content 待解密内容
     * @param key     解密密钥
     * @return
     */
    public static String decryptAES(String content, String key) {
        try {
            SecretKeySpec keySpec = genSecretKeySpec(null, key);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] result = cipher.doFinal(EncodeUtil.decodeHex(content));
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据keySize 和 key生成SecretKey
     *
     * @param keySize
     * @param key
     * @return SecretKeySpec
     */
    private static SecretKeySpec genSecretKeySpec(Integer keySize, String key) {
        if (keySize == null) {
            keySize = AES_DEFAULT_KEYSIZE;
            logger.debug("keySize is null take default key size {}", AES_DEFAULT_KEYSIZE);
        } else {
            if (keySize != AesKeySize.SIZE_128.size && keySize != AesKeySize.SIZE_256.size && keySize != AesKeySize.SIZE_512.size) {
                logger.error("keySize {} does not match requirement", keySize);
                throw new RuntimeException("keySize " + keySize + " does not match requirement");
            }
        }
        if (!StringUtil.isNotBlank(key)) {
            logger.debug("sed key is blank take default sed key");
        }
        byte[] sed = StringUtil.isNotBlank(key) ? key.getBytes() : DEFAULT_KEY;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(AES);
            kgen.init(keySize, new SecureRandom(sed));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            return new SecretKeySpec(enCodeFormat, AES);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public enum AesKeySize {
        SIZE_128(128),
        SIZE_256(256),
        SIZE_512(512),
        ;
        public int size;

        AesKeySize(int size) {
            this.size = size;
        }
    }

    public enum AesModel {
        ECB,
        CBC,
        CTR,
        OFB,
        CFB,
    }

    public enum Padding {
        pkcs5padding,
        pkcs7padding,
        zeropadding,
        iso10126,
        ansix923,
        ;
    }

}