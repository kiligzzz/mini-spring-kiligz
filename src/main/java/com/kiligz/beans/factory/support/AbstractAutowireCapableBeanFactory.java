package com.kiligz.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.kiligz.beans.BeansException;
import com.kiligz.beans.PropertyValue;
import com.kiligz.beans.factory.config.AutowireCapableBeanFactory;
import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.beans.factory.config.BeanPostProcessor;
import com.kiligz.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 抽象的自动装配可用bean的工厂
 * 定义创建bean的方法
 *
 * @author Ivan
 * @date 2022/8/11 17:19
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
        implements AutowireCapableBeanFactory {

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
        bean = initializeBean(beanName, bean, beanDefinition);
        // 分隔符
        System.out.println("=========================");
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

    /**
     * 为bean填充属性
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                if (value instanceof BeanReference) {
                    // beanA依赖beanB，先实例化beanB
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

    /**
     * 初始化bean
     */
    protected Object initializeBean(String beanName, Object existingBean, BeanDefinition beanDefinition) {
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(existingBean, beanName);
        invokeInitMethods(beanName, wrappedBean, beanDefinition);
        return applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
    }

    /**
     * 执行BeanPostProcessors的postProcessBeforeInitialization方法
     */
    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        return applyBeanPostProcessor(existingBean, beanName, true);
    }

    /**
     * 执行BeanPostProcessors的postProcessAfterInitialization方法
     */
    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        return applyBeanPostProcessor(existingBean, beanName, false);
    }

    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) {
        //TODO 后面会实现
        System.out.println("执行bean[" + beanName + "]的初始化方法");
    }

    /**
     * 执行BeanPostProcessors的方法
     */
    protected Object applyBeanPostProcessor(Object existingBean, String beanName, boolean isBefore) {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = isBefore ?
                    processor.postProcessBeforeInitialization(result, beanName) :
                    processor.postProcessAfterInitialization(result, beanName);
            if (current == null)
                break;
            result = current;
        }
        return result;
    }
}
