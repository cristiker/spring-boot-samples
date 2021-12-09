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
@TableName(value = "sys_permission_tree")
public class PermissionTree implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer permissionTreeId;
    /**
     * 菜单树编码
     */
    @TableField
    private String treeCode;
    /**
     * 权限ID
     */
    @TableField
    private String permissionId;
    /**
     * 上级权限ID
     */
    @TableField
    private Integer parentPermissionId;

    /**
     * 菜单顺序
     */
    @TableField
    private Integer orderNum;
}