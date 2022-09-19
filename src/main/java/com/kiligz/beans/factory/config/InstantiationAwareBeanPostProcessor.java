package com.kiligz.beans.factory.config;

import com.kiligz.beans.BeansException;

/**
 * 实例化感知bean接口，可生成代理对象
 *
 * @author Ivan
 * @date 2022/9/19 10:19
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * 在bean实例化之前执行
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}
