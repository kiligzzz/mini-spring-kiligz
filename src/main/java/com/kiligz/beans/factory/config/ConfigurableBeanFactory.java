package com.kiligz.beans.factory.config;

import com.kiligz.util.StringValueResolver;
import com.kiligz.beans.factory.HierarchicalBeanFactory;

/**
 * 可配置bean工厂接口
 *
 * @author Ivan
 * @date 2022/8/22 11:01
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    /**
     * 添加允许修改或替换实例化后的bean的扩展，存在则覆盖
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例bean
     */
    void destroySingletons();

    /**
     * 添加嵌入式Value解析器
     */
    void addEmbeddedValueResolver(StringValueResolver resolver);

    /**
     * 解析嵌入式的Value
     */
    String resolveEmbeddedValue(String value);
}
