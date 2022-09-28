package com.kiligz.beans.factory.config;

import com.kiligz.beans.BeansException;

/**
 * 允许修改或替换实例化后的bean的扩展
 *
 * @author Ivan
 * @date 2022/9/5 10:48
 */
public interface BeanPostProcessor {
    /**
     * 在bean执行初始化方法之前执行此方法
     */
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 在bean执行初始化方法之后执行此方法
     */
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
