package com.cristik.sample.entity.po;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zhenghua.ni
 */
@Data
@Accessors(chain = true)
@Table(name = "t_test")
public class Test {

    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "deleted")
    private Boolean deleted;

}