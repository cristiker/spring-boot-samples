package com.cristik.boot.samples.quartz.entity;

import com.cristik.boot.common.SpringContextHolder;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.Date;


/**
 * @author cristik
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SyncJobFactory extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(SyncJobFactory.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        ScheduleJob scheduleJob = (ScheduleJob) mergedJobDataMap.get(ScheduleJobBO.JOB_PARAM_KEY);
        logger.info("SyncJobFactory execute job:" + scheduleJob.getJobName());
        try {
            try {
                ScheduleJobLog scheduleJobLog = new ScheduleJobLog();
                scheduleJobLog.setScheduleJobId(scheduleJob.getId());
                scheduleJobLog.setUpdateDate(new Date());
                scheduleJobLog.setIpAddress(InetAddress.getLocalHost().getHostAddress());
                Object logObj = SpringContextHolder.getBean("scheduleJobServiceImpl");
                Method logMethod = logObj.getClass().getMethod("insertLog", ScheduleJobLog.class);
                logMethod.invoke(logObj, scheduleJobLog);
            } catch (Exception e) {
                logger.error("SyncJobFactory insertLog error:", e);
            }

            Object obj = SpringContextHolder.getBean(scheduleJob.getBeanName());
            Method method = obj.getClass().getMethod(scheduleJob.getMethodName());
            method.invoke(obj);
        } catch (Exception e) {
            logger.error("SyncJobFactory execute error:", e);
        }
    }
}
