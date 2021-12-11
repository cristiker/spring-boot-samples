package com.cristik.sample.entity.po;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.Options;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zhenghua.ni
 */
@Data
@Accessors(chain = true)
@Table(name = "t_fruit")
public class Fruit {

    @Id()
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fruit_name")
    private String fruitName;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "deleted")
    private Boolean deleted;

}