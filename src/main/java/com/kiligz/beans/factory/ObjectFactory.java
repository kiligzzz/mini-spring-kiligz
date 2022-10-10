package com.kiligz.beans.factory;

import com.kiligz.beans.BeansException;

/**
 * @author Ivan
 * @date 2022/10/9 17:15
 */
public interface ObjectFactory<T> {
    T getObject() throws BeansException;
}
