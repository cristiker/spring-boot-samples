package com.cristik.utils.lang;


import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;

import java.util.List;

/**
 * @author cristik
 */
public class BeanUtil extends BeanUtils {


    public static <T> T copyProperties(Object fromObject, Class<T> toObjectType) {
        T target = null;
        try {
            target = toObjectType.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        org.springframework.beans.BeanUtils.copyProperties(fromObject, target);
        return target;
    }

    public static <T> List<T> convertBeans(List<?> fromObjects, Class<T> toObjectType) {
        List<T> list = Lists.newArrayList();
        for (Object object : fromObjects) {
            list.add(copyProperties(object, toObjectType));
        }
        return list;
    }
}
