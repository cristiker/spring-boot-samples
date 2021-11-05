package com.cristik.sample.framework.response.message;

import com.cristik.sample.framework.enums.MessageCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author zhenghua.ni
 */
public class ResponseData<T> {

    private static final String DEFAULT_SUCCESS_CODE = MessageCodeEnum.SUCCESS.getCode();
    private static final String DEFAULT_SUCCESS_MESSAGE = MessageCodeEnum.SUCCESS.getMessage();

    private static final String DEFAULT_FAILED_CODE = MessageCodeEnum.FAILED.getCode();
    private static final String DEFAULT_FAILED_MESSAGE = MessageCodeEnum.FAILED.getMessage();

    private static final String DEFAULT_ERROR_CODE = MessageCodeEnum.SYSTEM_ERROR.getCode();
    private static final String DEFAULT_ERROR_MESSAGE = MessageCodeEnum.SYSTEM_ERROR.getMessage();

    private boolean success;
    private String code;
    private T data;
    private String message;
    private String traceId;

    public ResponseData() {
    }

    public ResponseData(boolean success, String code, T data, String message) {
        this.success = success;
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public ResponseData(boolean success, String code, T data, String message, String traceId) {
        this.success = success;
        this.code = code;
        this.data = data;
        this.message = message;
        this.traceId = traceId;
    }

    public static ResponseData success() {
        return new ResponseData(true, DEFAULT_SUCCESS_CODE, null, DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> ResponseData<T> success(T data) {
        return new ResponseData<>(true, DEFAULT_SUCCESS_CODE, data, DEFAULT_SUCCESS_MESSAGE);
    }

    public static ResponseData failed() {
        return new ResponseData(false, DEFAULT_FAILED_CODE, null, DEFAULT_FAILED_MESSAGE);
    }

    public static ResponseData failed(String message) {
        return new ResponseData(false, DEFAULT_FAILED_CODE, null, message);
    }


    public static ResponseData error() {
        return new ResponseData(false, DEFAULT_ERROR_CODE, null, DEFAULT_ERROR_MESSAGE);
    }

    public static ResponseData error(String message) {
        return new ResponseData(false, DEFAULT_ERROR_CODE, null, message);
    }

    public static ResponseData error(Result result) {
        return new ResponseData(false, result.getCode(), null, result.getMessage());
    }

}
