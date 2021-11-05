package com.cristik.utils.message;

import com.alibaba.fastjson.JSON;
import com.cristik.utils.exception.ResponseInfo;
import com.cristik.utils.utils.RequestUtil;

import java.util.Map;

/**
 * @author cristik
 */
public class JsonMessage {

    private String code;

    private Map<String, Object> data;

    private String requestId;

    private String message;

    private boolean success;

    public JsonMessage(ResponseInfo responseInfo, boolean success, Map<String, Object> data) {
        this.requestId = RequestUtil.getRequestId();
        this.code = responseInfo.getCode();
        this.message = responseInfo.getMsg();
        this.success = success;
        this.data = data;
    }

    public JsonMessage(String code, String message, boolean success, Map<String, Object> data) {
        this.requestId = RequestUtil.getRequestId();
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return JSON.toJSON(this).toString();
    }

}
