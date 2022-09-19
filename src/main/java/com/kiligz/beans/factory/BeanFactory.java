package com.kiligz.beans.factory;

import com.kiligz.beans.BeansException;

/**
 * bean工厂
 * 定义获取bean的方法
 *
 * @author Ivan
 * @date 2022/8/11 17:22
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;

    <T> T getBean(String beanName, Class<T> beanClass) throws BeansException;
}
