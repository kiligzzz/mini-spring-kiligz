package com.kiligz.beans.factory;

import com.kiligz.beans.BeansException;

/**
 * 三级缓存的存储介质，临时存储并延迟触发获取逻辑
 *
 * @author Ivan
 * @date 2022/10/9 17:15
 */
public interface ObjectFactory<T> {
    T getObject() throws BeansException;
}
