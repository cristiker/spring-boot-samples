package com.cristik.sample.framework.exception;

import com.cristik.sample.framework.enums.MessageCodeEnum;
import com.cristik.sample.framework.response.message.Result;

/**
 * @author zhenghua.ni
 */
public class BusinessException extends RuntimeException {

    private String code;

    private String message;

    public BusinessException() {
        this(MessageCodeEnum.FAILED.getCode(), MessageCodeEnum.FAILED.getMessage(), null);
    }

    public BusinessException(Result result) {
        this(result.getCode(), result.getMessage(), null);
    }

    public BusinessException(Throwable throwable, String message) {
        this(MessageCodeEnum.FAILED.getCode(), message, throwable);
    }

    public BusinessException(String message) {
        this(MessageCodeEnum.FAILED.getCode(), message, null);
    }

    public BusinessException(String code, String message) {
        this(code, message, null);
    }

    public BusinessException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
        this.message = message;
    }
}
