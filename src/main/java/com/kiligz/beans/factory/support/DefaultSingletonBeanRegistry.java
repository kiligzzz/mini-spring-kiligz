package com.kiligz.beans.factory.support;

import com.kiligz.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认的单例bean注册方法实现
 *
 * @author Ivan
 * @date 2022/8/11 17:22
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 单例bean表
     */
    private final Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * 注册单例bean
     */
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        addSingleton(beanName, singletonObject);
    }

    /**
     * 获取单例bean
     */
    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /**
     * 添加单例bean
     */
    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}
