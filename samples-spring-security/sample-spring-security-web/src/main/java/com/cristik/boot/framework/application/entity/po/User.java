package com.cristik.sample.framework.application.entity.po;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author cristik
 */
@Table(name = "upms_user")
public class User implements Serializable {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    /**
     * 帐号
     */
    @Column
    private String userName;
    /**
     * 密码MD5(密码+盐)
     */
    @Column
    private String password;
    /**
     * 盐
     */
    @Column
    private String salt;
    /**
     * 姓名
     */
    @Column
    private String realName;
    /**
     * 头像
     */
    @Column
    private String avatar;
    /**
     * 电话
     */
    @Column
    private String phone;
    /**
     * 邮箱
     */
    @Column
    private String email;
    /**
     * 性别 --0 男 --1 女)
     */
    @Column
    private Integer sex;
    /**
     * 类型(0:系统用户，1:普通用户)
     */
    @Column
    private Integer type;
    /**
     * 状态(0:正常,1:锁定)
     */
    @Column
    private Integer locked;
    /**
     * 创建时间
     */
    @Column
    private Date createDate;

    public User() {
    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}