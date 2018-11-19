package com.cristik.boot.application.aspect;

import com.cristik.common.utils.MessageUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 匹配Controller中第2,4,6,8有BindingResult类型参数的方法
 * @author cristik
 */

@Aspect
@Component
@Order(AopOrder.BIND_RESULT)
public class ControllerBindResultAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllerBindResultAspect.class);

    /**
     * controller下所有方法
     */
    @Pointcut(value = "within(@org.springframework.stereotype.Controller *)")
    public void controller() {

    }

    /**
     * RestController下所有方法
     */
    @Pointcut(value = "within(@org.springframework.web.bind.annotation.RestController *)")
    public void restController() {

    }

    /**
     * 参数中有BindingResult的方法
     */
    @Pointcut(value = "args(* ,org.springframework.validation.BindingResult,..)")
    public void bindResult2() {

    }

    /**
     * 参数中有BindingResult的方法
     */
    @Pointcut(value = "args(*,*,*,org.springframework.validation.BindingResult,..)")
    public void bindResult4() {

    }

    /**
     * 参数中有BindingResult的方法
     */
    @Pointcut(value = "args(*,*,*,*,*,org.springframework.validation.BindingResult,..)")
    public void bindResult6() {

    }

    /**
     * 参数中有BindingResult的方法
     */
    @Pointcut(value = "args(*,*,*,*,*,*,*,org.springframework.validation.BindingResult,..)")
    public void bindResult8() {

    }

    @Pointcut(value = "(controller()||restController())&&(bindResult2()||bindResult4()||bindResult6()||bindResult8())")
    public void pointCut() {

    }

    /**
     * 环绕通知
     */
    @Around(value = "pointCut()")
    public Object aroundService(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        List<BindingResult> bindingResults = Arrays.stream(args).filter(arg -> arg instanceof BindingResult
                && ((BindingResult) arg).hasErrors()).map(bindResult -> (BindingResult) bindResult)
                .collect(Collectors.toList());
        if (bindingResults != null && bindingResults.size() > 0) {
            logger.info("RequestId={},Type=Controller, Target={}.{},Binding with Error");
            return MessageUtil.error(bindingResults.toArray(new BindingResult[bindingResults.size()]));
        }
        return pjp.proceed();
    }

}
