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
@TableName(value="sys_i18n_language")
public class Language implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer languageId;
    /**
     * 语言编码
     */
     @TableField
    private String languageCode;
    /**
     * 语言
     */
     @TableField
    private String language;
    /**
     * 语言图标
     */
     @TableField
    private String languageIcon;
    /**
     * 排序
     */
     @TableField
    private Integer orderNum;
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