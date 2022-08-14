package com.kiligz.beans.factory.support;

import com.kiligz.beans.factory.config.BeanDefinition;

/**
 * BeanDefinition注册表
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
}