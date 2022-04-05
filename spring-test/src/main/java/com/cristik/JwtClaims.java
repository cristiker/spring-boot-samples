package com.cristik;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author cristik
 */
@Data
@Accessors(chain = true)
public class JwtClaims {
    /**
     * 用户
     */
    private String sub;
    /**
     * 接收方
     */
    private String aud;
    /**
     * 唯一身份标识
     */
    private String jti;
    /**
     * 签发者
     */
    private String iss;
    /**
     * 生效日期时间
     */
    private Date nbf;
    /**
     * 签发时间
     */
    private Date iat;
    /**
     * 有效时间,单位为秒
     */
    private Long duration;
}
