package com.cristik.samples.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author zhenghua.ni
 */
@Data
@Builder
@Accessors(chain = true)
@TableName("sys_product")
public class User {

    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;

    @TableField(value = "nick_name")
    private String nickName;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "avatar")
    private String avatar;

    @TableField(value = "password")
    private String password;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "create_by")
    private Long createBy;

    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "update_by")
    private Long updateBy;

}
