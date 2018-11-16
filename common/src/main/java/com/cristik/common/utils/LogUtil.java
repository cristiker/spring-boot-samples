package com.cristik.common.utils;

import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cristik
 */
public class LogUtil {

    public static final String BEGIN_TIME = "system.servlet.begin_time";

    public static final String CURRENT_TIME = "system.servlet.current_time";

    public static void logRequestTime(Logger logger) {
        if (RequestUtil.getRequest() == null) {
            return;
        }
        Long logTime = System.currentTimeMillis();
        HttpServletRequest request = RequestUtil.getRequest();
        String url = RequestUtil.getRequest().getRequestURI();
        if (request.getAttribute(LogUtil.CURRENT_TIME) != null
                && request.getAttribute(LogUtil.BEGIN_TIME) != null) {
            //开始时间
            Long beginTime = (Long) request.getAttribute(LogUtil.BEGIN_TIME);
            //上次记录时间
            Long lastTime = (Long) request.getAttribute(LogUtil.CURRENT_TIME);
            //累计耗时
            Long allCost = logTime - beginTime;
            //距离上次耗时
            Long periodCost = logTime - lastTime;
            logger.info("The request {} take {} ms in all and {} ms from lastTime", url, allCost, periodCost);
        } else {
            request.setAttribute(LogUtil.BEGIN_TIME, logTime);
            logger.info("The request {} take {} ms in all and {} ms from lastTime", url, 0, 0);
        }
        //更新上次耗时时间
        request.setAttribute(LogUtil.CURRENT_TIME, logTime);
    }
}
