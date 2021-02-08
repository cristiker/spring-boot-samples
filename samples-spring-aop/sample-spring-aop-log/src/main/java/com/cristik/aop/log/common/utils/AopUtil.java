package com.cristik.aop.log.common.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.CodeSignature;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author cristik
 */
public class AopUtil {

    private AopUtil() {
    }

    public static List<Parameter> parseArgs(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return new ArrayList<>();
        }
        Signature signature = joinPoint.getSignature();
        if (signature instanceof CodeSignature) {
            CodeSignature codeSignature = (CodeSignature) signature;
            return IntStream.range(0, codeSignature.getParameterNames().length).mapToObj(i -> {
                Parameter parameter = new Parameter();
                parameter.setParameterType(codeSignature.getParameterTypes().length > i ? codeSignature.getParameterTypes()[i] : null);
                parameter.setParameterName(codeSignature.getParameterNames().length > i ? codeSignature.getParameterNames()[i] : null);
                parameter.setParameterValue(args[i]);
                return parameter;
            }).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public static class Parameter {

        private String parameterName;

        private Object parameterValue;

        private Class parameterType;

        public String getParameterName() {
            return parameterName;
        }

        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }

        public Object getParameterValue() {
            return parameterValue;
        }

        public void setParameterValue(Object parameterValue) {
            this.parameterValue = parameterValue;
        }

        public Class getParameterType() {
            return parameterType;
        }

        public void setParameterType(Class parameterType) {
            this.parameterType = parameterType;
        }
    }

}