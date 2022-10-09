package com.kiligz.util;

import com.kiligz.core.convert.ConversionService;

import java.lang.reflect.Type;

/**
 * 类型转换工具类
 *
 * @author Ivan
 * @date 2022/10/9 11:00
 */
public class ConvertUtil {
    /**
     * 类型转换，不可以转换返回原来的，可以转换返回转换后的
     */
    public static Object convert(ConversionService conversionService, Object value, Class<?> targetType) {
        Class<?> sourceType = value.getClass();
        if (conversionService != null) {
            if (conversionService.canConvert(sourceType, targetType)) {
                value = conversionService.convert(value, targetType);
            }
        }
        return value;
    }

    /**
     * 类型转换，接收Type类型的目标类型
     */
    public static Object convert(ConversionService conversionService, Object value, Type targetType) {
        return convert(conversionService, value, (Class<?>) targetType);
    }
}
