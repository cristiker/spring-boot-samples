package com.cristik.utils.exception;

/**
 * @author cristik on 2016/7/20.
 * 11XX 表示资源可用性
 * 12XX 表示登录相关
 * 13XX Token相关
 * 14XX 表示权限相关
 * 15XX 表示校验相关
 */
public enum ResponseInfo {
    SUCCESS("200", "成功"),
    FAILED("400", "失败"),
    ERROR("500", "系统错误"),
    UNAUTHORIZED("401", "认证失败"),
    FORBIDDEN("403", "无权限访问"),
    VALIDATE_ERROR("400", "参数校验失败"),

    /**
     * 110X 表示资源可用性
     */
    INVALID_USERNAME("1101", "用户名不可用"),
    INVALID_PHONE("1102", "手机号码不可用"),
    INVALID_EMAIL("1103", "邮箱不可用"),
    /**
     * 111X 表示资源状态
     */
    PHONE_NOT_ACTIVE("1111", "手机未激活"),
    EMAIL_NOT_ACTIVE("1112", "邮箱未激活"),

    /**
     * 112X 表记录
     */
    RECORD_NOT_EXIST("1121", "记录不存在"),

    /**
     * 12XX 表示登录相关
     */
    LOGIN_REQUIRED("1201", "请登录后再访问"),

    /**
     * 121X 表示凭证相关
     */
    INCORRECT_CREDENTIALS("1211", "凭证错误"),
    EXPIRED_CREDENTIALS("1212", "凭证已过期"),
    CREDENTIALS("1213", "凭证异常"),

    /**
     * 122X 表示账号相关
     */
    UNKNOWN_ACCOUNT("1221", "账户不存在"),
    DISABLED_ACCOUNT("1222", "账号不可用"),
    LOCKED_ACCOUNT("1223", "账户被锁定"),
    CONCURRENT_ACCESS("1224", "账号已登录不能同时登录"),
    EXCESSIVE_ATTEMPTS("1225", "尝试次数过多请稍后再试"),
    ACCOUNT("1226", "账户异常"),

    CAS_AUTHENTICATION("1231", "CAS错误"),
    INVALID_TOKEN("1241", "Token无效"),
    UNSUPPORTED_TOKEN("1242", "Token类型不支持"),
    ;


    private String code;
    private String msg;

    ResponseInfo(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}