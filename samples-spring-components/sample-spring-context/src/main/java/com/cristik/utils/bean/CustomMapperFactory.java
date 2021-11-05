package com.cristik.utils.bean;

import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * Desc: 自定义orika 拷贝新增java8与之前日期字段相互转换
 *
 * @author wei1.sun
 * @date 2020/7/22 10:30
 * @see ma.glasnost.orika.converter.builtin.BuiltinConverters#register(ConverterFactory) 默认类型转换器
 * 默认转换器
 * 1 java8之前日期相互转换
 * 2 数字格式相互转换
 * 3 java8之前日期与long 等
 * @see Java8DateAndTimeConverters#register(ConverterFactory) (ConverterFactory)  java8日期与java之前日期类型相互转换
 *  自定义转换器功能包含
 *   1 java8日期类型相互转换
 *   2 java8日期类型与long相互转换
 *   3 java8与java8之前版本日期相互转换
 **/
public class CustomMapperFactory extends DefaultMapperFactory {
    /**
     * Constructs a new instance of DefaultMapperFactory
     *
     * @param builder
     */
    protected CustomMapperFactory(MapperFactoryBuilder<?, ?> builder) {
        super(builder);
        Java8DateAndTimeConverters.register(this.converterFactory);
    }

    public static class Builder extends MapperFactoryBuilder<DefaultMapperFactory, Builder> {

        @Override
        public DefaultMapperFactory build() {
            CustomMapperFactory customMapperFactory = new CustomMapperFactory(this);
            return customMapperFactory;
        }

        @Override
        protected Builder self() {
            return this;
        }
    }



}