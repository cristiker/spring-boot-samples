package com.cristik.sample.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;

import java.beans.Transient;
import java.util.Date;

/**
 * @author nizhenghua
 * @date 2021/12/09
 */
public class BaseEntity {

    @TableField
    private String createBy;

    @TableField
    private Date createTime;

    @TableField
    private String updateBy;

    @TableField
    private Date updateTime;

    @TableField
    private Integer status;

    private String sts;

}
