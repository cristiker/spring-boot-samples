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
 * 通用类型任务
 * @author cristik
 */
@Data
@Accessors(chain = true)
@TableName(value = "sys_job")
public class Job implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long jobId;

    @TableField
    private String jobName;

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
     * cron执行表达式
     */
    @TableField
    private String cronExpression;
    /**
     * 计划执行错误策略（1立即执行 2执行一次 3放弃执行）
     */
    @TableField
    private Integer misfirePolicy;
    /**
     * 状态（0正常 1暂停）
     */
    @TableField
    private Integer status;
    /**
     * 创建者
     */
    @TableField
    private String createBy;
    /**
     * 创建时间
     */
    @TableField
    private Date createTime;
    /**
     * 更新者
     */
    @TableField
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField
    private Date updateTime;
    /**
     * 备注信息
     */
    @TableField
    private String remark;
}
