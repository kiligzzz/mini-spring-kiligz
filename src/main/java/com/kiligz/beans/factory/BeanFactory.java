package com.kiligz.beans.factory;

import com.kiligz.beans.BeansException;

/**
 * @author Ivan
 * @date 2022/8/11 17:22
 */
public interface BeanFactory {
    Object getBean(String name) throws BeansException;
}
