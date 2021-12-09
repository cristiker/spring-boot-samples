package com.cristik.sample.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author cristik
 */
@Data
@Accessors(chain = true)
@TableName(value = "sys_role")
public class Role extends BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer roleId;
    /**
     * 角色类型
     */
    @TableField
    private String roleType;
    /**
     * 角色编码
     */
    @TableField
    private String roleCode;
    /**
     * 角色名称
     */
    @TableField
    private String roleName;
    /**
     * 是否系统角色
     */
    @TableField
    private Integer sys;
    /**
     * 备注
     */
    @TableField
    private String description;
    /**
     * 备注
     */
    @TableField
    private String remark;
    /**
     * 排序
     */
    @TableField
    private Integer orderNum;
}