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
@TableName(value = "sys_dict_data")
public class DictData extends BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer dictDataId;
    /**
     * 字典类型
     */
    @TableField
    private String dictTypeCode;
    /**
     * 字典数据内容
     */
    @TableField
    private String dictValue;
    /**
     * 字典数据标签
     */
    @TableField
    private String dictLabel;
    /**
     * 字典数据标签i18n编码
     */
    @TableField
    private String dictLabelCode;
    /**
     * 是否系统值
     */
    @TableField
    private Boolean sys;

    /**
     * 是否系统默认值
     */
    @TableField
    private Boolean sysDefault;
    /**
     * 描述
     */
    @TableField
    private String description;

    /**
     * 排序
     */
    @TableField
    private Integer orderNum;
    /**
     * 备注
     */
    @TableField
    private String remark;
}

