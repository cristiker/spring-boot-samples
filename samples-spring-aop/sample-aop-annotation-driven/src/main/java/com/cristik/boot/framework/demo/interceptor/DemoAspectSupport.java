package com.cristik.boot.framework.demo.interceptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;


/**
 * @author cristik
 */
public class DemoAspectSupport implements BeanFactoryAware, InitializingBean {

    @Nullable
    protected Object invokeWithDemo(Method method, @Nullable Class<?> targetClass) {
        return null;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
