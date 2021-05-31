package com.cristik.common.message;

import java.util.UUID;

/**
 * @author zhenghua.ni
 */
public class ResponseData<T> {

    public static final String DEFAULT_SUCCESS_CODE = "200";
    public static final String DEFAULT_SUCCESS_MESSAGE = "success";

    public static final String DEFAULT_ERROR_CODE = "400";
    public static final String DEFAULT_ERROR_MESSAGE = "failed";

    public Boolean success;

    public String code;

    public T data;

    public String message;

    public String requestId;

    public static <T> ResponseData<T> success() {
        return new ResponseData<>(true, DEFAULT_SUCCESS_CODE, null, DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> ResponseData<T> successData(T data) {
        return new ResponseData<>(true, DEFAULT_SUCCESS_CODE, data, DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> ResponseData<T> error(String code, String message) {
        return new ResponseData<>(false, DEFAULT_ERROR_CODE, null, DEFAULT_ERROR_MESSAGE);
    }

    private ResponseData(Boolean success, String code, T data, String message) {
        this.success = success;
        this.code = code;
        this.data = data;
        this.message = message;
        this.requestId = UUID.randomUUID().toString();
    }
}