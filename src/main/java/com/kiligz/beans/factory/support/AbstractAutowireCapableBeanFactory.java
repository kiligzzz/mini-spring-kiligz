package com.kiligz.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.PropertyValue;
import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

/**
 * 抽象的自动装配可用bean的工厂
 * 定义创建bean的方法
 *
 * @author Ivan
 * @date 2022/8/11 17:19
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    /**
     * Bean实例化策略
     */
    private final InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    /**
     * 根据BeanDefinition创建bean，创建完成后加入单例的bean表中
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = createBeanInstance(beanDefinition, args);
        applyPropertyValues(beanName, bean, beanDefinition);
        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * 依据策略实例化bean
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition, Object[] args) {
        if (args == null)
            return instantiationStrategy.instantiate(beanDefinition, null, null);

        Constructor<?> ctorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        for (Constructor<?> ctor : beanClass.getDeclaredConstructors()) {
            if (ctor.getParameterTypes().length == args.length) {
                ctorToUse = ctor;
                break;
            }
        }
        return instantiationStrategy.instantiate(beanDefinition, ctorToUse, args);
    }

    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            for (PropertyValue propertyValue : beanDefinition.getPropertyValueList()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                if (value instanceof BeanReference) {
                    // （bean）A依赖B，获取B的实例
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                // 属性填充
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values：" + beanName);
        }
    }
}
