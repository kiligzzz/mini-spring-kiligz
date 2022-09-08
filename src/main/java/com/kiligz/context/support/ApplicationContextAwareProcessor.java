package com.kiligz.context.support;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.config.BeanPostProcessor;
import com.kiligz.context.ApplicationContext;
import com.kiligz.context.ApplicationContextAware;

/**
 * 通过BeanPostProcessor实现感知所属ApplicationContext功能
 *
 * @author Ivan
 * @date 2022/9/8 09:54
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {
    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            System.out.println("------------------> [ set applicationContext ]");
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
