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
@TableName(value="sys_translate_group")
public class TranslateGroup extends BaseEntity implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer groupId;
    /**
     * 分组编号
     */
     @TableField
    private String code;
    /**
     * 描述
     */
     @TableField
    private String description;
    /**
     * 备注
     */
     @TableField
    private String remark;
}