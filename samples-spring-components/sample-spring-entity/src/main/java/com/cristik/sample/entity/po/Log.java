package com.cristik.sample.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zhenghua
 */
@Data
@Accessors(chain = true)
@TableName(value = "sys_log")
public class Log implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long logId;

    @TableField
    private String requestId;
    /**
     * 操作描述
     */
    @TableField
    private String description;
    /**
     * 操作用户
     */
    @TableField
    private String userName;
    /**
     * 操作时间
     */
    @TableField
    private Long startTime;
    /**
     * 消耗时间
     */
    @TableField
    private Integer spendTime;
    /**
     * 根路径
     */
    @TableField
    private String basePath;
    /**
     * URI
     */
    @TableField
    private String uri;
    /**
     * URL
     */
    @TableField
    private String url;
    /**
     * 请求类型
     */
    @TableField
    private String method;
    /**
     * 请求参数
     */
    @TableField
    private String parameter;
    /**
     * 用户标识
     */
    @TableField
    private String userAgent;
    /**
     * IP地址
     */
    @TableField
    private String ip;
    /**
     * 响应结果
     */
    @TableField
    private String result;
    /**
     * 权限值
     */
    @TableField
    private String permissions;
}
