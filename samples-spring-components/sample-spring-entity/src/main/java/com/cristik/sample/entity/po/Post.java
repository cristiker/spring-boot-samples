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
@TableName(value="sys_post")
public class Post implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer postId;
    /**
     * 岗位编码
     */
     @TableField
    private String postCode;
    /**
     * 岗位名称
     */
     @TableField
    private String postName;
    /**
     * 显示顺序
     */
     @TableField
    private Integer postSort;
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
    /**
     * 备注
     */
     @TableField
    private String remark;
}