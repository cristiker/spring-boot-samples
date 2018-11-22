package com.cristik.boot;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cristik
 */
public class CustomCacheApplicationTest {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void main(String[] args) {
        Date date = new Date(0L);
        Date date2 = new Date(1000L);
        System.out.println(sdf.format(date));
        System.out.println(sdf.format(date2));

    }

}