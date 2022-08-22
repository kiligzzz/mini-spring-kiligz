package com.kiligz.beans.factory.support;

import com.kiligz.beans.BeansException;
import com.kiligz.core.io.Resource;
import com.kiligz.core.io.ResourceLoader;

/**
 * 读取BeanDefinition的接口
 *
 * @author Ivan
 * @date 2022/8/22 14:19
 */
public interface BeanDefinitionReader {
    /**
     * 获取BeanDefinition注册处
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 获取资源加载器
     */
    ResourceLoader getResourceLoader();

    /**
     * 通过location数组加载BeanDefinition
     */
    void loadBeanDefinitions(String[] locations) throws BeansException;

    /**
     * 通过location加载BeanDefinition
     */
    void loadBeanDefinitions(String location) throws BeansException;

    /**
     * 通过Resource加载BeanDefinition
     */
    void loadBeanDefinitions(Resource resource) throws BeansException;
}
