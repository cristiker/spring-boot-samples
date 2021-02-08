package com.cristik.aop.demo.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * @author cristik
 */
public class DemoInterceptor extends DemoAspectSupport implements MethodInterceptor, Serializable {

    @Nullable
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invokeWithDemo(invocation.getMethod(), null);
    }


}