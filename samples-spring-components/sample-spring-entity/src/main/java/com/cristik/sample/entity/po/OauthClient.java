package com.cristik.sample.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author cristik
 */

@Data
@Accessors(chain = true)
@TableName(value = "sys_oauth_client")
public class OauthClient {

    @TableId(type = IdType.AUTO)
    private Long oauthClientId;

    @TableField
    private String clientName;

    @TableField
    private String clientId;

    @TableField
    private String clientSecret;

    @TableField
    private String redirectDomain;

}
