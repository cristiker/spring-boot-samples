package com.cristik.utils.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author cristik
 */
public class RequestUtil {
    private static final String APPLICATION_JSON = MediaType.APPLICATION_JSON.toString();

    private static Logger logger = LoggerFactory.getLogger(RequestUtil.class);

    private static String REQUEST_ID = "requestId";
    private static ThreadLocal<HttpServletRequest> _request = new ThreadLocal<>();
    private static ThreadLocal<HttpServletResponse> _response = new ThreadLocal<>();

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = _request.get();
        return request;
    }

    public static void setRequest(HttpServletRequest request) {
        _request.set(request);
        setRequestId();
    }

    public static void removeRequest() {
        _request.remove();
    }

    public static HttpServletResponse getResponse() {
        HttpServletResponse response = _response.get();
        return response;
    }

    public static void setResponse(HttpServletResponse response) {
        _response.set(response);
    }

    public static void removeResponse() {
        _response.remove();
    }

    public static String getRequestId() {
        String requestId = null;
        if (_request.get() != null) {
            requestId = (String) _request.get().getAttribute(REQUEST_ID);
        }
        return requestId;
    }

    /**
     * 判断是否按AJAX请求处理
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return isJsonResponse(request) || isAjax(request) || isJsonRequest(request);
    }

    /**
     * 判断是否需要返回JSON
     *
     * @param request
     * @return
     */
    public static boolean isJsonResponse(HttpServletRequest request) {
        return APPLICATION_JSON.equalsIgnoreCase(request.getHeader("accept"));
    }

    /**
     * 判断是否是AJAX
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    /**
     * 判断是否是JSON请求
     *
     * @param request
     * @return
     */
    public static boolean isJsonRequest(HttpServletRequest request) {
        return APPLICATION_JSON.equalsIgnoreCase(request.getHeader("content-type"));
    }

    private static void setRequestId() {
        String requestId = UUID.randomUUID().toString();
        _request.get().setAttribute(REQUEST_ID, requestId);
    }

}
