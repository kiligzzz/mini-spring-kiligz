package com.kiligz.beans.factory.support;

import com.kiligz.beans.BeansException;
import com.kiligz.core.io.DefaultResourceLoader;
import com.kiligz.core.io.ResourceLoader;

/**
 * 抽象的BeanDefinition读取类
 *
 * @author Ivan
 * @date 2022/8/22 14:20
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    /**
     * 使用默认的资源加载器初始化
     */
    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    /**
     * 使用指定的资源加载器初始化
     */
    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    /**
     * 设置资源加载器
     */
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 获取BeanDefinition注册处
     */
    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    /**
     * 获取资源加载器
     */
    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    /**
     * 通过location数组加载BeanDefinition，调用单个location加载方法
     */
    @Override
    public void loadBeanDefinitions(String[] locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }
}
