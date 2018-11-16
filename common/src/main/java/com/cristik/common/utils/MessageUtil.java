package com.cristik.common.utils;

import com.cristik.common.exception.BusinessException;
import com.cristik.common.exception.ResponseInfo;
import com.cristik.common.message.JsonMessage;
import com.google.common.collect.Maps;
import org.springframework.validation.BindingResult;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cristik
 */
public class MessageUtil {

    public static String success() {
        return success(ResponseInfo.SUCCESS);
    }

    public static String success(String message) {
        return success(ResponseInfo.SUCCESS.getCode(), message);
    }

    public static String success(ResponseInfo responseInfo) {
        return success(responseInfo.getCode(), responseInfo.getMsg());
    }

    public static String success(String key, Object object) {
        Map<String, Object> data = Maps.newHashMap();
        data.put(key, object);
        return success(data);
    }

    public static String success(Map<String, Object> data) {
        return success(ResponseInfo.SUCCESS, data);
    }

    public static String success(String code, String message) {
        return success(code, message, null);
    }

    public static String success(ResponseInfo responseInfo, Map<String, Object> data) {
        return success(responseInfo.getCode(), responseInfo.getMsg(), data);
    }

    public static String success(String code, String message, Map<String, Object> data) {
        return buildJsonResult(code, message, true, data);
    }


    public static String error() {
        return error(ResponseInfo.FAILED);
    }

    public static String error(String message) {
        return error(ResponseInfo.FAILED.getCode(), message);
    }

    public static String error(Exception exception) {
        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception;
            return error(businessException.getCode(), businessException.getMessage());
        } else {
            return error(ResponseInfo.FAILED.getCode(), exception.getMessage());
        }
    }

    public static String error(BindingResult... bindingResults) {
        Map<String, Object> data = Maps.newHashMap();
        Map<String, String> errors = new LinkedHashMap<>();
        data.put("errors", errors);
        for (BindingResult bindingResult : bindingResults) {
            if (bindingResult.hasErrors()) {
                bindingResult.getFieldErrors().stream().forEach(fieldError ->
                        errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            }
        }
        return error(ResponseInfo.VALIDATE_ERROR, data);
    }

    public static String error(ResponseInfo responseInfo) {
        return error(responseInfo, null);
    }

    public static String error(String code, String message) {
        return error(code, message, null);
    }

    public static String error(ResponseInfo responseInfo, Map<String, Object> data) {
        return buildJsonResult(responseInfo.getCode(), responseInfo.getMsg(), false, data);
    }


    public static String error(String code, String message, Map<String, Object> data) {
        return buildJsonResult(code, message, false, data);
    }

    private static String buildJsonResult(String code, String message, boolean successResponse
            , Map<String, Object> data) {
        JsonMessage jsonMessage = new JsonMessage(code, message, successResponse, data);
        return jsonMessage.toString();
    }

}
