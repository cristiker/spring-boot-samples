package com.cristik.sample.framework.enums;

import com.cristik.sample.framework.response.message.Result;

/**
 * @author zhenghua.ni
 */
public enum MessageCodeEnum implements Result {

    SYSTEM_ERROR("500", "系统错误", "系统错误"),
    FAILED("400", "请求失败", "请求失败"),
    SUCCESS("200", "请求成功", "success"),
    ;

    private String code;
    private String message;
    private String desc;

    MessageCodeEnum(String code, String message, String desc) {
        this.code = code;
        this.desc = desc;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDesc() {
        return desc;
    }
}
