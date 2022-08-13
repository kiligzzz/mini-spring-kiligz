package com.kiligz.beans.factory.support;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * Bean实例化策略
 * 定义Bean实例化方法
 *
 * @author Ivan
 * @date 2022/8/13 16:18
 */
public interface InstantiationStrategy {

    /**
     * 实例化Bean
     */
    Object instantiate(BeanDefinition beanDefinition, Constructor<?> ctor, Object[] args) throws BeansException;
}
