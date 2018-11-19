package com.cristik.boot.application.aspect;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cristik.boot.common.utils.AopUtil;
import com.cristik.common.lang.StringUtil;
import com.google.common.base.Stopwatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
    @Pointcut(value = "@annotation(com.cristik.boot.application.aspect.IgnoreLog)")
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
        //记录进入controller
        logger.info("Action=Enter, Type=Controller, Target={}.{}", joinPoint.getTarget().getClass().getSimpleName()
                , joinPoint.getSignature().getName());
        List<AopUtil.Parameter> parameters = AopUtil.parseArgs(joinPoint);
        String requestParameter = null;
        try {
            parameters = parameters.stream().filter(parameter ->
                    !(parameter.getParameterValue() instanceof HttpServletRequest)
                            && !(parameter.getParameterValue() instanceof HttpServletResponse)
                            && !(parameter.getParameterValue() instanceof BindingResult)).collect(Collectors.toList());

            requestParameter = JSONArray.toJSONString(parameters);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.debug("Action=Log, Type=Controller, Target={}.{}, Parameters={}"
                , joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName()
                , StringUtil.isNotEmpty(requestParameter) ? requestParameter : "[]");
    }

    /**
     * 环绕通知
     */
    @Around(value = "webController()")
    public Object aroundController(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        Stopwatch stopwatch = Stopwatch.createStarted();
        Object object = pjp.proceed();
        stopwatch.stop();
        logger.warn("Type=Controller, Target={}.{}, Cost={}mills"
                , pjp.getTarget().getClass().getSimpleName(), pjp.getSignature().getName()
                , stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return object;
    }

    /**
     * 后置通知
     */
    @After("webController()")
    public void afterController(JoinPoint joinPoint) {
        //记录controller完成时间
        logger.info("Action=Exit, Type=Controller, Target={}.{}", joinPoint.getTarget().getClass().getSimpleName()
                , joinPoint.getSignature().getName());
    }

    /**
     * 异常通知
     */
    @AfterThrowing(pointcut = "webController()", throwing = "ex")
    public void afterControllerThrowing(JoinPoint joinPoint, Throwable ex) {
        //记录Exception警告事件外层捕捉Exception
        logger.warn("Action=Exception, Type=Controller, Target={}.{}, Exception={}"
                , joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), ex);
    }

    /**
     * 返回通知
     */
    @AfterReturning(pointcut = "webController()", returning = "retVal")
    public void afterControllerReturning(JoinPoint joinPoint, Object retVal) {
        String returnJson;
        if (retVal instanceof ResponseEntity) {
            returnJson = retVal.toString();
        } else {
            returnJson = JSONObject.toJSONString(retVal);
        }
        //记录正常结束返回值
        logger.debug("Action=Return, Type=Controller, Target={}.{}, Return={}"
                , joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), returnJson);
    }

}