package com.kiligz.context.support;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.config.BeanPostProcessor;
import com.kiligz.context.ApplicationContext;
import com.kiligz.context.ApplicationContextAware;
import com.kiligz.util.LogUtil;

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
            LogUtil.setApplicationContext();

            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }
}
