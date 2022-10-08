package com.kiligz.core.convert.support;

/**
 * 默认的类型转换服务
 *
 * @author Ivan
 * @date 2022/9/28 15:34
 */
public class DefaultConversionService extends GenericConversionService {
    public DefaultConversionService() {
        addDefaultConvertors();
    }

    /**
     * 添加默认的转换器
     */
    public void addDefaultConvertors() {
        addConverterFactory(new StringToNumberConverterFactory());
    }
}
