package com.cristik.boot.application.quartz.entity;

import com.cristik.boot.common.SpringContextHolder;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.Date;

public class AsyncJobFactory extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(AsyncJobFactory.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ScheduleJobBO scheduleJobMVO = (ScheduleJobBO) context.getMergedJobDataMap().get(ScheduleJobBO.JOB_PARAM_KEY);
        logger.info("AsyncJobFactory execute job:" + scheduleJobMVO.getJobName());
        Thread t = new Thread(() -> {
            try {
                try {
                    ScheduleJobLog scheduleJobLog = new ScheduleJobLog();
                    scheduleJobLog.setScheduleJobId(scheduleJobMVO.getId());
                    scheduleJobLog.setUpdateDate(new Date());
                    scheduleJobLog.setIpAddress(InetAddress.getLocalHost().getHostAddress());
                    Object logObj = SpringContextHolder.getBean("scheduleJobServiceImpl");
                    Method logMethod = logObj.getClass().getMethod("insertLog", ScheduleJobLog.class);
                    logMethod.invoke(logObj, scheduleJobLog);
                } catch (Exception e) {
                    logger.error("AsyncJobFactory insertLog error:", e);
                }

                Object obj = SpringContextHolder.getBean("producerMonitorMessage");
                Method method = obj.getClass().getMethod("scheduleTasks", Integer.class);
                method.invoke(obj, scheduleJobMVO.getId());
            } catch (Exception e) {
                logger.error("AsyncJobFactory execute error:", e);
            }
        });
        t.start();
    }
}
