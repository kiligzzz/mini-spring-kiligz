package com.kiligz.beans.factory.support;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.BeanFactory;
import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.beans.factory.config.ConfigurableBeanFactory;

/**
 * 抽象的bean工厂，提供获取bean的方法
 * 定义获取BeanDefinition方法、创建bean的方法
 *
 * @author Ivan
 * @date 2022/8/11 17:21
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory {

    /**
     * 延迟加载，在使用bean时才加载创建bean，使用前以BeanDefinition保存对应信息
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        return doGetBean(beanName, null);
    }

    /**
     * 延迟加载，在使用bean时才加载创建bean，使用前以BeanDefinition保存对应信息（带参数）
     */
    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return doGetBean(beanName, args);
    }

    /**
     * 延迟加载bean的实现
     */
    protected Object doGetBean(String beanName, Object[] args) {
        Object bean = getSingleton(beanName);
        if (bean != null)
            return bean;

        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName, beanDefinition, args);
    }

    /**
     * 获取BeanDefinition，延迟实现
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 根据BeanDefinition创建bean，延迟实现
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

}
