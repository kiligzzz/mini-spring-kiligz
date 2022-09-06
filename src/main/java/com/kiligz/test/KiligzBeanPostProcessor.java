package com.kiligz.test;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.config.BeanPostProcessor;

/**
 * @author Ivan
 * @date 2022/9/5 16:21
 */
public class KiligzBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("------------------> [ postProcess before ]");
        if ("kiligzService".equals(beanName)) {
            ((KiligzService) bean).setPrefix("z:");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("------------------> [ postProcess after ]");
        return bean;
    }
}
