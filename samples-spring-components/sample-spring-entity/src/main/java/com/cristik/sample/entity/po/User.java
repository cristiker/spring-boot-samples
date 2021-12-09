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
@TableName(value = "sys_user")
public class User extends BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long userId;
    /**
     * 用户uid
     */
    @TableField
    private String uid;
    /**
     * 租户ID
     */
    @TableField
    private Long tenantId;
    /**
     * 用户名
     */
    @TableField
    private String userName;
    /**
     * 昵称
     */
    @TableField
    private String nickName;
    /**
     * 密码MD5(密码+盐)
     */
    @TableField
    private String password;
    /**
     * 盐
     */
    @TableField
    private String salt;
    /**
     * 名字
     */
    @TableField
    private String lastName;
    /**
     * 姓氏
     */
    @TableField
    private String firstName;
    /**
     * 真是姓名
     */
    @TableField
    private String realName;
    /**
     * 身份证号码
     */
    @TableField
    private String idCard;

    /**
     * 生日
     */
    @TableField
    private Date birthday;
    /**
     * 头像
     */
    @TableField
    private String avatar;
    /**
     * 手机号
     */
    @TableField
    private String phone;
    /**
     * 手机号验证
     */
    @TableField
    private Integer phoneStatus;
    /**
     * 邮箱
     */
    @TableField
    private String email;
    /**
     * 邮箱验证
     */
    @TableField
    private Integer emailStatus;
    /**
     * 性别 --0 男 --1 女)
     */
    @TableField
    private Integer sex;
    /**
     * 类型(0:系统用户，1:普通用户)
     */
    @TableField
    private Integer type;
    /**
     * 状态(0:正常,1:锁定)
     */
    @TableField
    private Integer locked;
}
