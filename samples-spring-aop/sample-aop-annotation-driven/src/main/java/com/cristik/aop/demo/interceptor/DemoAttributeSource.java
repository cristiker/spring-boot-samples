package com.cristik.aop.demo.interceptor;

import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * @author cristik
 */
public interface DemoAttributeSource {

    DemoAttribute getDemoAttribute(Method method, @Nullable Class<?> targetClass);
}