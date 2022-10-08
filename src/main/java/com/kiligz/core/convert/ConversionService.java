package com.kiligz.core.convert;

/**
 * 类型转换接口
 *
 * @author Ivan
 * @date 2022/9/28 15:20
 */
public interface ConversionService {
    /**
     * 能否转换
     */
    boolean canConvert(Class<?> sourceType, Class<?> targetType);

    /**
     * 将source转换为targetType类型
     */
    <S, T> T convert(S source, Class<T> targetType);
}
