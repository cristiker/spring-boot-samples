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
@TableName(value = "cron")
public class Cron implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField
    private String cronExpression;
    @TableField
    private String cronText;
    @TableField
    private Integer status;
    @TableField
    private String sts = "A";
    @TableField
    private Date createDate;
    @TableField
    private Date updateDate;

    public Cron() {
    }

    public Cron(Integer cronId) {
        this.id = cronId;
    }
}