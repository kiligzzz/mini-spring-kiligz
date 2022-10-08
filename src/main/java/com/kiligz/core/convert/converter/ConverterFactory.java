package com.kiligz.core.convert.converter;

/**
 * 转换器工厂接口
 *
 * @author Ivan
 * @date 2022/9/28 15:27
 */
public interface ConverterFactory<S, T> {
    /**
     * 根据目标类型获取转换器
     */
    <R extends T> Converter<S, R> getConverter(Class<R> targetType);
}
