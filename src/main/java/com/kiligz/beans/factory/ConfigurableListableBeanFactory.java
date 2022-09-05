package com.kiligz.beans.factory;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.config.AutowireCapableBeanFactory;
import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.beans.factory.config.ConfigurableBeanFactory;

/**
 * 可配置的、可列出的bean工厂接口
 *
 * @author Ivan
 * @date 2022/8/22 11:02
 */
public interface ConfigurableListableBeanFactory extends AutowireCapableBeanFactory,
        ConfigurableBeanFactory, ListableBeanFactory {
    /**
     * 获取BeanDefinition，用于BeanFactoryPostProcessor自定义修改BeanDefinition的属性值
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;
}
