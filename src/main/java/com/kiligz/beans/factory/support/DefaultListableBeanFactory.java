package com.kiligz.beans.factory.support;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.ConfigurableListableBeanFactory;
import com.kiligz.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认的bean注册表工厂
 *
 * @author Ivan
 * @date 2022/8/11 17:22
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    /**
     * 注册表
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    /**
     * 向注册表中注册BeanDefinition，外部可访问
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    /**
     * 是否包含指定名称的BeanDefinition
     */
    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    /**
     * 获取BeanDefinition，用于BeanFactoryPostProcessor自定义修改BeanDefinition的属性值
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null)
            throw new BeansException("No bean named '" + beanName + "' is defined");
        return beanDefinition;
    }

    /**
     * 提前实例化所有单例实例
     */
    @Override
    public void preInstantiateSingletons() throws BeansException {
        System.out.println("---> [ pre instantiation Singletons ] ");
        beanDefinitionMap.keySet().forEach(this::getBean);
    }

    /**
     * 返回指定类型的所有bean实例
     */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> typeBeansMap = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class<?> beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                Object bean = getBean(beanName);
                typeBeansMap.put(beanName, type.cast(bean));
            }
        });
        return typeBeansMap;
    }

    /**
     * 返回定义的所有bean的名称
     */
    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }
}
