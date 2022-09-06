package com.kiligz.beans.factory.support;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.BeanFactory;
import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.beans.factory.config.BeanPostProcessor;
import com.kiligz.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象的bean工厂，提供获取bean的方法
 * 定义获取BeanDefinition方法、创建bean的方法
 *
 * @author Ivan
 * @date 2022/8/11 17:21
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

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
        System.out.printf("-------> [ get bean: %s ]%n", beanName);

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

    /**
     * 添加允许修改或替换实例化后的bean的扩展，存在则覆盖
     */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessorList.remove(beanPostProcessor);
        beanPostProcessorList.add(beanPostProcessor);
    }

    /**
     * 获取所有BeanPostProcessor
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessorList;
    }
}
