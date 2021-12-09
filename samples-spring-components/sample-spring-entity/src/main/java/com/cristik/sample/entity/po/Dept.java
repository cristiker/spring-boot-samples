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
@TableName(value="sys_dept")
public class Dept implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer deptId;

    /**
     * 部门ID
     */
    @TableField
    private Integer parentId;
    /**
     * 上级列表
     */
     @TableField
    private String ancestors;
    /**
     * 部门名称
     */
     @TableField
    private String deptName;
    /**
     * 顺序
     */
     @TableField
    private Integer orderNum;
    /**
     * 负责人
     */
     @TableField
    private String leader;
    /**
     * 手机号
     */
     @TableField
    private String phone;
    /**
     * 邮箱
     */
     @TableField
    private String email;
    /**
     * 状态
     */
     @TableField
    private Integer status;
    /**
     * 创建人
     */
     @TableField
    private String createBy;
    /**
     * 创建时间
     */
     @TableField
    private Date createTime;
    /**
     * 修改人
     */
     @TableField
    private String updateBy;
    /**
     * 修改时间
     */
     @TableField
    private Date updateTime;
}