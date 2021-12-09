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
@TableName(value = "sys_permission")
public class Permission extends BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer permissionId;
    /**
     * 权限所属系统
     */
    @TableField
    private String permissionSystemCode;
    /**
     * 名称
     */
    @TableField
    private String permissionName;
    /**
     * 名称编码
     */
    @TableField
    private String permissionNameCode;
    /**
     * 类型(1:目录,2:菜单,3:按钮)
     */
    @TableField
    private String permissionType;
    /**
     * 权限值
     */
    @TableField
    private String permissionCode;
    /**
     * 路径
     */
    @TableField
    private String uri;
    /**
     * 方法
     */
    @TableField
    private String method;
    /**
     * 图标
     */
    @TableField
    private String icon;
}