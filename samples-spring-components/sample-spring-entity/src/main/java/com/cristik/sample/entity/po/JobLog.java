package com.cristik.sample.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cristik
 */
@Data
@Accessors(chain = true)
@TableName(value = "sys_job_log")
public class JobLog implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long jobLogId;
    /**
     * 任务名称
     */
    @TableField
    private String jobName;
    /**
     * 任务组名
     */
    @TableField
    private String jobGroup;
    /**
     * 任务方法
     */
    @TableField
    private String methodName;
    /**
     * 方法参数
     */
    @TableField
    private String methodParams;
    /**
     * 日志信息
     */
    @TableField
    private String jobMessage;
    /**
     * 执行状态（0正常 1失败）
     */
    @TableField
    private Integer status;
    /**
     * 异常信息
     */
    @TableField
    private String exceptionInfo;
    @TableField
    private String jobResult;
    /**
     * 创建时间
     */
    @TableField
    private Date createTime;
}
