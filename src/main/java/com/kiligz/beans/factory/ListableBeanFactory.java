package com.kiligz.beans.factory;

import com.kiligz.beans.BeansException;

import java.util.Map;

/**
 * 可列出的Bean工厂接口
 *
 * @author Ivan
 * @date 2022/8/22 10:59
 */
public interface ListableBeanFactory extends BeanFactory {
    /**
     * 返回指定类型的所有bean实例
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 返回定义的所有bean的名称
     */
    String[] getBeanDefinitionNames();
}
