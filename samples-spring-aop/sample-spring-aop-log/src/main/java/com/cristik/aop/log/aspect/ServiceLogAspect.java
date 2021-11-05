package com.cristik.aop.log.aspect;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cristik.aop.log.common.utils.AopUtil;
import com.cristik.utils.lang.StringUtil;
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

/**
 * 匹配Service方法打印参数日志
 *
 * @author cristik
 */

@Aspect
@Component
@Order(AopOrder.LOG)
public class ServiceLogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void service() {

    }

    @Pointcut("execution(* *.create*(..))")
    public void create() {

    }

    @Pointcut("execution(* *.delete*(..))")
    public void delete() {

    }

    @Pointcut("execution(* *.update*(..))")
    public void update() {

    }

    @Pointcut("execution(* *.query*(..))")
    public void query() {

    }

    /**
     * 有ignoreLog注解的方法
     */
    @Pointcut(value = "@annotation(com.cristik.aop.log.aspect.IgnoreLog)")
    public void ignoreLog() {

    }

    @Pointcut(value = "service() && !ignoreLog()")
    public void webService() {

    }

    /**
     * 切面的前置方法 即方法执行前拦截到的方法 记录并输出
     * 在目标方法执行之前的通知
     */
    @Before(value = "webService()")
    public void beforeServiceAdvice(JoinPoint joinPoint) {
        //记录进入service
        logger.info("Action=Enter, Type=Service, Target={}.{}"
                , joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName());
        List<AopUtil.Parameter> parameters = AopUtil.parseArgs(joinPoint);
        String requestParameter = null;
        try {
            requestParameter = JSONArray.toJSONString(parameters);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        //打印service参数
        logger.debug("Action=Log, Type=Service, Target={}.{}, Parameters={}"
                , joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName()
                , StringUtil.isNotEmpty(requestParameter) ? requestParameter : "[]");
    }

    /**
     * 环绕通知
     */
    @Around(value = "webService()")
    public Object aroundService(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        Stopwatch stopwatch = Stopwatch.createStarted();
        Object object = pjp.proceed();
        stopwatch.stop();
        logger.warn("Type=Service, Target={}.{}, Cost={}mills"
                , pjp.getTarget().getClass().getSimpleName(), pjp.getSignature().getName()
                , stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return object;
    }

    /**
     * 切面的后置方法，不管抛不抛异常都会走此方法
     * 在目标方法执行之后的通知
     */
    @After("webService()")
    public void afterService(JoinPoint joinPoint) {
        //记录service完成时间
        logger.info("Action=Exit, Type=Service, Target={}.{}", joinPoint.getTarget().getClass().getSimpleName()
                , joinPoint.getSignature().getName());
    }

    /**
     * 在目标方法非正常执行完成 发生异常 抛出异常的时候会走此方法
     * 获得异常可以用throwing
     */
    @AfterThrowing(pointcut = "webService()", throwing = "ex")
    public void afterServiceThrowing(JoinPoint joinPoint, Throwable ex) {
        //记录Exception警告事件外层捕捉Exception
        logger.warn("Action=Exception, Type=Service, Target={}.{}, Exception={}"
                , joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), ex);
    }

    /**
     * 在方法正常执行通过之后执行的通知叫做返回通知
     * 可以返回到方法的返回值 在注解后加入returning
     */
    @AfterReturning(pointcut = "webService()", returning = "retVal")
    public void afterServiceReturning(JoinPoint joinPoint, Object retVal) {
        String returnJson = JSONObject.toJSONString(retVal);
        //记录正常结束返回值
        logger.debug("Action=Return, Type=Service, Target={}.{}, Return={}"
                , joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), returnJson);
    }
}