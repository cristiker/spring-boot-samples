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
@TableName(value="sys_user_dept")
public class UserDept implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer userDeptId;
    /**
     * 用户ID
     */
     @TableField
    private Long userId;
    /**
     * 部门ID
     */
     @TableField
    private Integer deptId;
}
