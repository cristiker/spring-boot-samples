package com.cristik.sample.entity.po;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author cristik
 */
@Data
@Accessors(chain = true)
public class UrlFilter {
    private Integer id;
    /**
     * 名称
     */
    private String name;

    /**
     * URL
     */
    private String url;

    /**
     * 方法
     */
    private String method;

    /**
     * 所需角色
     */
    private String roles;

    /**
     * 所需要的权限
     */
    private String permissions;
}
