package com.kiligz.context.support;

import com.kiligz.beans.factory.FactoryBean;
import com.kiligz.beans.factory.InitializingBean;
import com.kiligz.beans.factory.annotation.Autowired;
import com.kiligz.beans.factory.annotation.Qualifier;
import com.kiligz.core.convert.ConversionService;
import com.kiligz.core.convert.converter.Converter;
import com.kiligz.core.convert.converter.ConverterFactory;
import com.kiligz.core.convert.converter.GenericConverter;
import com.kiligz.core.convert.support.DefaultConversionService;
import com.kiligz.core.convert.support.GenericConversionService;
import com.kiligz.stereotype.Component;
import com.kiligz.util.LogUtil;

import java.util.Set;

/**
 * 类型转换工厂bean
 *
 * @author Ivan
 * @date 2022/10/8 17:45
 */
//@Component
public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {
    /**
     * 转换器集合，通过converterFactoryBean
     */
    @Autowired
    @Qualifier("converters")
    Set<?> converters;

    /**
     * 通用的类型转换服务
     */
    private GenericConversionService conversionService;

    /**
     * bean初始化方法
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        LogUtil.registerConversionServiceAndConverters();

        conversionService = new DefaultConversionService();
        registerConverters();
    }

    /**
     * 获取bean
     */
    @Override
    public ConversionService getObject() throws Exception {
        return conversionService;
    }

    /**
     * 是否是单例
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * 注册转换器
     */
    private void registerConverters() {
        if (converters == null) return;

        for (Object converter : converters) {
            if (converter instanceof GenericConverter) {
                conversionService.addConverter((GenericConverter) converter);
            } else if (converter instanceof Converter) {
                conversionService.addConverter((Converter<?, ?>) converter);
            } else if (converter instanceof ConverterFactory) {
                conversionService.addConverterFactory((ConverterFactory<?, ?>) converter);
            } else {
                throw new IllegalArgumentException("Each converter object must implement one of the " +
                        "Converter、 ConverterFactory、 GenericConverter interfaces");
            }
        }
    }
}
