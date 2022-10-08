package com.kiligz.core.convert.converter;

/**
 * 转换器接口
 *
 * @author Ivan
 * @date 2022/9/28 15:25
 */
public interface Converter<S, T> {
    /**
     * 类型转换
     */
    T convert(S source);
}
