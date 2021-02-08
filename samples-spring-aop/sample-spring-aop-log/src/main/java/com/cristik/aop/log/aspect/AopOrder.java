package com.cristik.aop.log.aspect;

/**
 * @author cristik
 */
public class AopOrder {

    private AopOrder() {
    }

    public static final int TRANSACTION = 0;

    public static final int LOG = 1;

    public static final int BIND_RESULT = 2;


}