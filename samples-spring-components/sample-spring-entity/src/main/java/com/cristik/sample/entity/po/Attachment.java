package com.cristik.sample.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cristik
 */
@Data
@Accessors(chain = true)
@TableName(value = "sys_attachment")
public class Attachment implements Serializable {

    @ApiModelProperty("附件ID")
    @TableId(type = IdType.AUTO)
    private Long attachmentId;

    @TableField
    @ApiModelProperty("类型")
    private String type;

    @TableField
    @ApiModelProperty("后缀")
    private String suffix;

    @TableField
    @ApiModelProperty("文件名")
    private String name;

    @TableField
    @ApiModelProperty("原始文件名")
    private String originalName;

    @TableField
    @ApiModelProperty("链接")
    private String url;

    @TableField
    @ApiModelProperty("阿里云存储key")
    private String objectKey;

    @TableField
    @ApiModelProperty("范围")
    private Integer scope;

    @TableField
    @ApiModelProperty("附件拥有人")
    private Long ownerId;

    @TableField
    @ApiModelProperty("存储类型")
    private Integer storageType;

    @TableField
    @ApiModelProperty("存储账号")
    private String storageAccount;

    @TableField
    @ApiModelProperty("创建时间")
    private Date createTime;

    @TableField
    @ApiModelProperty("更新时间")
    private Date updateTime;

    @TableField
    @ApiModelProperty("存储访问权限")
    private Integer storageAccess;
}
