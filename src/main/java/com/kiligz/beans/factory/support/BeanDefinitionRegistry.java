package com.kiligz.beans.factory.support;

import com.kiligz.beans.factory.config.BeanDefinition;

/**
 * BeanDefinition注册接口
 * 定义注册BeanDefinition的方法
 *
 * @author Ivan
 * @date 2022/8/11 17:21
 */
public interface BeanDefinitionRegistry {
    /**
     * 向注册表中注册BeanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 是否包含指定名称的BeanDefinition
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 向注册表中注册BeanDefinition，不允许重复的
     */
    void registerBeanDefinitionWithoutRepeated(String beanName, BeanDefinition beanDefinition);
}
