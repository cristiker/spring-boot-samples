package com.cristik.common.utils;

import com.cristik.common.message.Pagination;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author cristik
 */
public class FormatUtil {

    /**
     * 转换Pagination分页数据返回格式
     *
     * @param pagination
     * @return
     */
    public static Map<String, Object> format(Pagination pagination) {
        Map<String, Object> pageInfo = Maps.newHashMap();
        pageInfo.put("current", pagination.getPageNo());
        pageInfo.put("pageSize", pagination.getPageSize());
        pageInfo.put("total", pagination.getTotal());
        Map<String, Object> data = Maps.newHashMap();
        data.put("list", pagination.getData());
        data.put("pagination", pageInfo);
        return data;
    }

    public static void test() {
        System.out.println(111);
    }

}
