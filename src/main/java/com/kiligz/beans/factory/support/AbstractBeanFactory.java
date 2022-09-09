package com.kiligz.beans.factory.support;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.FactoryBean;
import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.beans.factory.config.BeanPostProcessor;
import com.kiligz.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final Map<String, Object> factoryBeanCache = new HashMap<>();

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

        Object sharedInstance = getSingleton(beanName);
        if (sharedInstance != null)
            return getObjectForBeanInstance(sharedInstance, beanName);

        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Object bean = createBean(beanName, beanDefinition, args);
        return getObjectForBeanInstance(bean, beanName);
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

    /**
     * 如果是FactoryBean，从FactoryBean#getObject中创建bean
     */
    public Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        Object bean = beanInstance;
        if (beanInstance instanceof FactoryBean<?>) {
            System.out.println("----------> [ FactoryBean get object ]");

            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            try {
                if (factoryBean.isSingleton()) {
                    bean = factoryBeanCache.get(beanName);
                    if (bean == null) {
                        bean = factoryBean.getObject();
                    }
                } else {
                    bean = factoryBean.getObject();
                }
            } catch (Exception e) {
                throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
            }
        }
        return bean;
    }
}
