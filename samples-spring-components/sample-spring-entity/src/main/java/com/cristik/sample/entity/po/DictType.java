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
@TableName(value = "sys_dict_type")
public class DictType extends BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer dictTypeId;

    @TableField
    private String dictName;

    @TableField
    private String dictNameCode;

    @TableField
    private String dictTypeCode;

    @TableField
    private Boolean sys;

    @TableField
    private String remark;

    @TableField
    private String description;
}
