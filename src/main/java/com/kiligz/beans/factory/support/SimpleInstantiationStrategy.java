package com.kiligz.beans.factory.support;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 使用Bean的构造函数来实例化
 *
 * @author Ivan
 * @date 2022/8/13 16:19
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    /**
     * 使用Bean的构造函数来实例化
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        Class<?> clazz = beanDefinition.getBeanClass();
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new BeansException("Failed to instantiate [" + clazz.getName() + "]", e);
        }
    }
}
