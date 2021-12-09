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
@TableName(value = "sys_user_post")
public class UserPost implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long userPostId;

    /**
     * 用户ID
     */
    @TableField
    private Long userId;
    /**
     * 职位ID
     */
    @TableField
    private Integer postId;
}
