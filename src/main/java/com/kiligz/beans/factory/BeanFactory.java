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
    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;
}
