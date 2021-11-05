package com.cristik.utils.exception;

/**
 * @author cristik
 */
public class RollbackBusinessException extends BusinessException {

    private String message;

    private String code;

    public RollbackBusinessException(ResponseInfo responseInfo) {
        this(responseInfo.getCode(), responseInfo.getMsg(), null);
    }

    public RollbackBusinessException(String message) {
        this(message, null);
    }

    public RollbackBusinessException(String message, Throwable throwable) {
        this(ResponseInfo.FAILED.getCode(), message, throwable);
    }

    public RollbackBusinessException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

}