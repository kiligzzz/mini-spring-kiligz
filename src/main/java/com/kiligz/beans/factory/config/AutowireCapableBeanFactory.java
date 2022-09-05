package com.kiligz.beans.factory.config;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.BeanFactory;

/**
 * 自动装配可用bean的工厂接口
 *
 * @author Ivan
 * @date 2022/8/22 11:00
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
