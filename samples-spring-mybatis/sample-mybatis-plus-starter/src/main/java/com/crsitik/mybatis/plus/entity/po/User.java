package com.crsitik.mybatis.plus.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author nizhenghua
 * @date 2021/12/08
 */
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField
    private String nickName;

    @TableField
    private String userName;

    @TableField
    private String realName;

    @TableField
    private Long createBy;

    @TableField
    private Long updateBy;

    @TableField
    private LocalDateTime createTime;

    @TableField
    private LocalDateTime updateTime;

    @TableField
    private Boolean deleted;

}
