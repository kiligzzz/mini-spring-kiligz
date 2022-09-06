package com.kiligz.test;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.PropertyValue;
import com.kiligz.beans.PropertyValues;
import com.kiligz.beans.factory.ConfigurableListableBeanFactory;
import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @author Ivan
 * @date 2022/9/5 16:20
 */
public class KiligzBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("------> [ postProcessBeanFactory ]");

        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("kiligzService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        // 后面的会覆盖前面配置的
        propertyValues.addPropertyValue(new PropertyValue("prefix", "z:"));
    }
}
