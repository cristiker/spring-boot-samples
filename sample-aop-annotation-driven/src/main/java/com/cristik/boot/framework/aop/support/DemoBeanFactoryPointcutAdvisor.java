package com.cristik.boot.framework.aop.support;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * @author cristik
 */
public class DemoBeanFactoryPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    @Override
    public Pointcut getPointcut() {
        return null;
    }
}
