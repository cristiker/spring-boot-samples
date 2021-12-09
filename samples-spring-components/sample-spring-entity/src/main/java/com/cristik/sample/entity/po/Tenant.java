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
@TableName(value = "sys_tenant")
public class Tenant implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long tenantId;

    @TableField
    private String tenantName;

    @TableField
    private String description;

    @TableField
    private String remark;

    @TableField
    private Integer state;

    @TableField
    private Integer type;

    @TableField
    private String createBy;

    @TableField
    private Date createTime;

    @TableField
    private String updateBy;

    @TableField
    private Date updateTime;
}
