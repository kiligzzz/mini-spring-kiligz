package com.kiligz.beans.factory.config;

/**
 * 单例bean注册表
 * 定义注册和获取单例bean的方法
 *
 * @author Ivan
 * @date 2022/8/11 17:19
 */
public interface SingletonBeanRegistry {

    /**
     * 注册单例bean
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 获取单例bean
     */
    Object getSingleton(String beanName);
}
