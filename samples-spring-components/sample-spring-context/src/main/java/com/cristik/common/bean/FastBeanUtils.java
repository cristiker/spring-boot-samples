package com.cristik.common.bean;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 主要是用来拷贝
 * CustomMapperFactory自定义factory继承了DefaultMapperFactory
 * 新增了java8与java8之后版本日期类型相互转换
 * orika默认工厂 builder 转换器
 * @see  ma.glasnost.orika.impl.DefaultMapperFactory
 * @see  ma.glasnost.orika.impl.DefaultMapperFactory.Builder
 * @see  ma.glasnost.orika.converter.builtin.BuiltinConverters
 * 自定义工厂 builder 转换器
 * @see CustomMapperFactory
 * @see CustomMapperFactory.Builder
 * @see Java8DateAndTimeConverters
 *
 * @Author: wei1.sun
 * @Date: 2020/7/21 21:04
 *
 */
@Slf4j
public class FastBeanUtils {

    /**
     * 默认字段工厂
     */
    private static MapperFactory MAPPER_FACTORY = new CustomMapperFactory.Builder().build();

    /**
     * 默认字段实例
     */
    private static final MapperFacade DEFAULT_MAPPER_FACADE = MAPPER_FACTORY.getMapperFacade();

    /**
     * 默认字段实例集合
     */
    private volatile static Map<String, MapperFacade> CACHE_MAPPER_FACADE_MAP = Maps.newHashMap();

    /**
     * 拷贝
     * @param source
     * @param tClass
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T copy(S source, Class<T> tClass) {
        return DEFAULT_MAPPER_FACADE.map(source,tClass);
    }

    /**
     * @Param: [source, tClass, configMap, ignoreProperties]
     * @Return: T
     * @Author: wei1.sun
     * @Date: 2020/7/21 21:04
     * 功能描述:
     */
    public static <S, T> T copy(S source, Class<T> tClass, Map<String,String> configMap,
                                String ... ignoreProperties) {
        if(MapUtils.isEmpty(configMap) && ArrayUtils.isEmpty(ignoreProperties)) {
            return copy(source,tClass);
        }
        MapperFacade mapperFacade = getMapperFacade(source.getClass(), tClass, configMap,ignoreProperties);
        return  mapperFacade.map(source,tClass);
    }

    /**
     * 批量拷贝
     * @param sourceList
     * @param targetClazz
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S,T> List<T> copyList(List<S> sourceList,Class<T> targetClazz) {
        if(CollectionUtils.isEmpty(sourceList)) {
            return Lists.newArrayList();
        }
        return DEFAULT_MAPPER_FACADE.mapAsList(sourceList, targetClazz);
    }

    /**
     * 映射集合（自定义配置）
     * @param sourceList    源数据
     * @param targetClazz   映射类
     * @param configMap    自定义配置 这个优先级高于ignoreProperties
     * @param ignoreProperties 排除源对象拷贝属性
     * @return 映射类对象
     */
    public static <S, T> List<T> copyList(List<S> sourceList,Class<T> targetClazz,
                                          Map<String, String> configMap,String ... ignoreProperties) {
        if(CollectionUtils.isEmpty(sourceList)) {
            return Lists.newArrayList();
        }
        if(MapUtils.isEmpty(configMap) && ArrayUtils.isEmpty(ignoreProperties)) {
            return copyList(sourceList,targetClazz);
        }
        S source = sourceList.get(0);
        MapperFacade mapperFacade = getMapperFacade(source.getClass(), targetClazz, configMap,ignoreProperties);
        return mapperFacade.mapAsList(sourceList, targetClazz);
    }

    /**
     * 获取自定义映射
     * @param sourceClazz   映射类
     * @param targetClazz 数据映射类
     * @param configMap    自定义配置 这个优先级高于ignoreProperties
     * @param ignoreProperties 排除源对象拷贝属性
     * @return 映射类对象
     */
    private static  <S, T> MapperFacade getMapperFacade(Class<S> sourceClazz, Class<T> targetClazz,
                                                        Map<String, String> configMap,String ... ignoreProperties) {
        String mapKey = sourceClazz.getCanonicalName() + "_" + targetClazz.getCanonicalName();
        if(!CACHE_MAPPER_FACADE_MAP.containsKey(mapKey)) {
            synchronized (FastBeanUtils.class) {
                if(!CACHE_MAPPER_FACADE_MAP.containsKey(mapKey)) {
                    MapperFactory factory = new CustomMapperFactory.Builder().build();
                    ClassMapBuilder<S,T> classMapBuilder = factory.classMap(sourceClazz, targetClazz);
                    Arrays.stream(Optional.ofNullable(ignoreProperties).orElse(new String[]{}))
                            .forEach(classMapBuilder::exclude);
                    Optional.ofNullable(configMap).orElse(Maps.newHashMap())
                            .forEach(classMapBuilder::field);
                    classMapBuilder.byDefault().register();
                    CACHE_MAPPER_FACADE_MAP.put(mapKey, factory.getMapperFacade());
                }
            }
        }
        return CACHE_MAPPER_FACADE_MAP.get(mapKey);
    }

}