/**
 * Copyright (c) 2005-2012 springside.org.cn
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.cristik.common.codec;

import com.cristik.common.lang.ExceptionUtil;
import com.cristik.common.lang.StringUtil;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 * 封装各种格式的编码解码工具类.
 * 1.Commons-Codec的 hex/base64 编码
 * 2.自制的base62 编码
 * 3.Commons-Lang的xml/html escape
 * 4.JDK提供的URLEncoder
 * @author cristik
 */
public class EncodeUtil {

    private static final Logger logger = LoggerFactory.getLogger(EncodeUtil.class);
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     *  预编译XSS过滤正则表达式
     */
    private static Pattern p1 = Pattern.compile("<\\s*(script|link|style|iframe)(.|\\n)*<\\s*\\/\\s*\\1\\s*>\\s*"
            , Pattern.CASE_INSENSITIVE);
    private static Pattern p2 = Pattern.compile("\\s*on[a-z]+\\s*=\\s*(\"[^\"]+\"|'[^']+'|[^\\s]+)\\s*(?=>)"
            , Pattern.CASE_INSENSITIVE);
    private static Pattern p3 = Pattern.compile("\\s*(href|src)\\s*=\\s*(\"\\s*(javascript|vbscript):[^\"]+" +
            "\"|'\\s*(javascript|vbscript):[^']+'|(javascript|vbscript):[^\\s]+)\\s*(?=>)", Pattern.CASE_INSENSITIVE);
    private static Pattern p4 = Pattern.compile("epression\\((.|\\n)*\\);?", Pattern.CASE_INSENSITIVE);

    /**
     * 预编译SQL过滤正则表达式
     */
    private static Pattern p5 = Pattern.compile("(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and" +
                    "|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)"
            , Pattern.CASE_INSENSITIVE);

    /**
     * encode byte[] to Hex String
     */
    public static String encodeHex(byte[] input) {
        return new String(Hex.encodeHex(input)).toUpperCase();
    }

    /**
     * decode Hex String to byte[]
     */
    public static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * Base64编码.
     * @param input
     * @return
     */
    public static String encodeBase64(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    /**
     * Base64编码.
     * @param input
     * @return
     */
    public static String encodeBase64(String input) {
        if (StringUtil.isNotBlank(input)) {
            return EncodeUtil.encodeBase64(input.getBytes());
        } else {
            return "";
        }
    }

    /**
     * Base64解码.
     */
    public static byte[] decodeBase64Bytes(String input) {
        return Base64.decodeBase64(input.getBytes());
    }

    /**
     * Base64解码.
     */
    public static String decodeBase64(String input) {
        try {
            return new String(Base64.decodeBase64(input.getBytes()), DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_'
     * 见RFC3548
     */
    public static String encodeUrlSafeBase64(byte[] input) {
        return new String(Base64.encodeBase64URLSafe(input));
    }

    /**
     * Base62编码。
     */
    public static String encodeBase62(byte[] input) {
        char[] chars = new char[input.length];
        for (int i = 0; i < input.length; i++) {
            chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
        }
        return new String(chars);
    }

    /**
     *
     * @param input
     * @return
     */
    public static String escapeJava(String input){
        return StringEscapeUtils.escapeJava(input);
    }

    /**
     * @param input
     * @return
     */
    public static String unescapeJava(String input){
        return StringEscapeUtils.unescapeJava(input);
    }

    /**
     * Html 转码.
     */
    public static String encodeHtml(String html) {
        return StringEscapeUtils.escapeHtml4(html);
    }

    /**
     * Html 解码.
     */
    public static String decodeHtml(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
    }

    /**
     * Xml 转码.
     */
    public static String encodeXml(String xml) {
        return StringEscapeUtils.escapeXml10(xml);
    }

    /**
     * Xml 解码.
     */
    public static String decodeXml(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    /**
     * URL 编码, Encode默认为UTF-8.
     */
    public static String encodeUrl(String part) {
        return encodeUrl(part, DEFAULT_ENCODING);
    }

    /**
     * URL 编码, Encode默认为UTF-8.
     */
    public static String encodeUrl(String part, String encoding) {
        if (part == null) {
            return null;
        }
        try {
            return URLEncoder.encode(part, encoding);
        } catch (UnsupportedEncodingException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * URL 解码, Encode默认为UTF-8.
     */
    public static String decodeUrl(String part) {
        return decodeUrl(part, DEFAULT_ENCODING);
    }

    /**
     * URL 解码, Encode默认为UTF-8.
     */
    public static String decodeUrl(String part, String encoding) {

        try {
            return URLDecoder.decode(part, encoding);
        } catch (UnsupportedEncodingException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * XSS 非法字符过滤
     * 内容以<!--HTML-->开头的用以下规则（保留标签，去掉js脚本）：
     * 	1、<(script|link|style|iframe)(.|\n)*<\/\1>\s*
     * 	2、\s*on[a-z]+\s*=\s*("[^"]+"|'[^']+'|[^\s]+)\s*(?=>)
     * 	3、\s*(href|src)\s*=\s*("\s*(javascript|vbscript):[^"]+"|'\s*(javascript|vbscript):[^']+'|(javascript|vbscript):[^\s]+)\s*(?=>)
     * 	4、epression\((.|\n)*\);?
     * 其它情况下：进行HTML4编码
     * @author ThinkGem
     */
    public static String xssFilter(String text) {
        if (text != null) {
            String oriValue = StringUtil.trim(text), value = oriValue;
            value = p1.matcher(value).replaceAll("");
            value = p2.matcher(value).replaceAll("");
            value = p3.matcher(value).replaceAll("");
            value = p4.matcher(value).replaceAll("");
            // 如果开始不是HTML，XML，JOSN格式，则再进行HTML的 "、<、> 转码。
            // HTML
            boolean flag = !StringUtil.startsWithIgnoreCase(value, "<!--HTML-->") &&
                    //XML
                    !StringUtil.startsWithIgnoreCase(value, "<?xml ") &&
                    // JFlow
                    !StringUtil.contains(value, "id=\"FormHtml\"") &&
                    // JSON Object
                    !(StringUtil.startsWith(value, "{") && StringUtil.endsWith(value, "}")) &&
                    // JSON Array
                    !(StringUtil.startsWith(value, "[") && StringUtil.endsWith(value, "]"));
            if (flag){
                value = value.replaceAll("\"", "&quot;").replaceAll("<", "&lt;")
                        .replaceAll(">", "&gt;");
            }
            if (logger.isInfoEnabled() && !value.equals(oriValue)) {
                logger.info("xssFilter: {} to {}", text, value);
            }
            return value;
        }
        return null;
    }

    /**
     * SQL过滤，防止注入，传入参数输入有select相关代码，替换空。
     */
    public static String sqlFilter(String text) {
        if (text != null) {
            String value = p5.matcher(text).replaceAll("");
            if (!value.equals(text)) {
                logger.warn("sqlFilter: {} to {}", text, value);
                return StringUtil.EMPTY;
            }
            return value;
        }
        return null;
    }
}
