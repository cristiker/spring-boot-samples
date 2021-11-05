package com.cristik.utils.exception;

/**
 * @author cristik
 */
public class BusinessException extends RuntimeException {

    private String message;

    private String code;

    public BusinessException(ResponseInfo responseInfo) {
        this(responseInfo.getCode(), responseInfo.getMsg(), null);
    }

    public BusinessException(String message) {
        this(message, null);
    }

    public BusinessException(String message, Throwable throwable) {
        this(ResponseInfo.FAILED.getCode(), message, throwable);
    }

    public BusinessException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
