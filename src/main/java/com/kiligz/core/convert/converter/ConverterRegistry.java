package com.kiligz.core.convert.converter;

/**
 * 转换器注册接口
 *
 * @author Ivan
 * @date 2022/9/28 15:30
 */
public interface ConverterRegistry {
    /**
     * 添加转换器
     */
    void addConverter(Converter<?, ?> converter);

    /**
     * 添加转换器工厂
     */
    void addConverterFactory(ConverterFactory<?, ?> converterFactory);

    /**
     * 添加通用转换器
     */
    void addConverter(GenericConverter converter);
}
