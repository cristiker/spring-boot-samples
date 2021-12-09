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
@TableName(value="sys_i18n_translate")
public class Translate implements Serializable{

    @TableId(type = IdType.AUTO)
    private Long translateId;
    /**
     * 字符串键
     */
     @TableField
    private String translateKey;
    /**
     * 文案分组
     */
    @TableField
    private String translateGroup;
    /**
     * 语言主键
     */
     @TableField
    private String languageCode;
    /**
     * 翻译值
     */
     @TableField
    private String translate;
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
     * 状态
     */
     @TableField
    private Integer status;
}
