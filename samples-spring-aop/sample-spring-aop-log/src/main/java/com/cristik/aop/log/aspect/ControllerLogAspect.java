package com.cristik.aop.log.aspect;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cristik.aop.log.common.utils.AopUtil;
import com.cristik.utils.lang.StringUtil;
import com.cristik.utils.utils.LogUtil;
import com.google.common.base.Stopwatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 匹配Controller方法打印参数日志
 *
 * @author cristik
 */
@Aspect
@Component
@Order(AopOrder.LOG)
public class ControllerLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);

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
     * 有IgnoreLog注解的方法
     */
    @Pointcut(value = "@annotation(com.cristik.framework.aspect.IgnoreLog)")
    public void excludeLog() {

    }

    /**
     * 聚合切点
     */
    @Pointcut(value = "((controller()||restController())) && !excludeLog()")
    public void webController() {

    }

    /**************************** controller advice  ***************************/

    /**
     * 前置通知
     */
    @Before("webController()")
    public void beforeController(JoinPoint joinPoint) {
        List<AopUtil.Parameter> parameters = AopUtil.parseArgs(joinPoint);
        String requestParameter = null;
        try {
            parameters = parameters.stream()
                    .filter(parameter -> LogUtil.allowPrintLog(parameter.getParameterValue()))
                    .collect(Collectors.toList());
            requestParameter = JSONArray.toJSONString(parameters);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.debug("Action=Enter, Type=Controller, Target={}#{}, Parameters={}"
                , joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName()
                , StringUtil.isNotEmpty(requestParameter) ? requestParameter : "[]");
    }

    /**
     * 环绕通知
     */
    @Around(value = "webController()")
    public Object aroundController(ProceedingJoinPoint pjp) throws Throwable {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Object object = pjp.proceed();
        stopwatch.stop();
        logger.warn("Type=Controller, Target={}#{}, Cost={}mills"
                , pjp.getTarget().getClass().getSimpleName(), pjp.getSignature().getName()
                , stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return object;
    }

    /**
     * 异常通知
     */
    @AfterThrowing(pointcut = "webController()", throwing = "ex")
    public void afterControllerThrowing(JoinPoint joinPoint, Throwable ex) {
        //记录Exception警告事件外层捕捉Exception
        logger.warn("Action=Exception, Type=Controller, Target={}#{}, Exception={}"
                , joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), ex);
    }

    /**
     * 返回通知
     */
    @AfterReturning(pointcut = "webController()", returning = "retVal")
    public void afterControllerReturning(JoinPoint joinPoint, Object retVal) {
        if (LogUtil.allowPrintLog(retVal)) {
            String returnJson = JSONObject.toJSONString(retVal);
            //记录正常结束返回值
            logger.debug("Action=Return, Type=Controller, Target={}#{}, Return={}"
                    , joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), returnJson);
        } else {
            logger.debug("Action=Return, Type=Controller, Target={}#{}, Ignore Return Type = {}"
                    , joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), retVal.getClass());
        }

    }
}