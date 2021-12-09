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
@TableName(value="sys_config")
public class Config extends BaseEntity implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer configId;

    /**
     * 参数组ID
     */
    @TableField
    private Integer configGroupId;

    /**
     * 参数名称
     */
     @TableField
    private String configName;
    /**
     * 参数名i18n
     */
    @TableField
    private String configNameCode;
    /**
     * 参数键名
     */
     @TableField
    private String configKey;

    /**
     * 参数键值
     */
     @TableField
    private String configValue;
    /**
     * 系统内置
     */
     @TableField
    private Integer configType;
    /**
     * 备注
     */
     @TableField
    private String remark;
}
