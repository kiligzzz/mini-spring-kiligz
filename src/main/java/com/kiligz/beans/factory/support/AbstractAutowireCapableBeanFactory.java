package com.kiligz.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.kiligz.beans.BeansException;
import com.kiligz.beans.PropertyValue;
import com.kiligz.beans.factory.BeanFactoryAware;
import com.kiligz.beans.factory.DisposableBean;
import com.kiligz.beans.factory.InitializingBean;
import com.kiligz.beans.factory.config.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

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
     * 根据BeanDefinition创建bean
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        System.out.printf("----------> [ create bean: %s ]%n", beanName);
        
        // 如果bean需要代理，则直接返回代理对象
        Object bean = resolveBeforeInstantiation(beanName, beanDefinition);
        if (bean != null)
            return bean;
        
        return doCreateBean(beanName, beanDefinition);
    }

    /**
     * 在实例化之前判断是否需要代理bean，执行InstantiationAwareBeanPostProcessor的方法
     */
    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanName, beanDefinition.getBeanClass());

        if (bean != null)
            // 只走了postProcess after ???
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);

        return bean;
    }

    /**
     * 根据BeanDefinition创建bean，创建完成后加入单例的bean表中
     */
    private Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = createBeanInstance(beanDefinition);
        applyPropertyValues(beanName, bean, beanDefinition);
        bean = initializeBean(beanName, bean, beanDefinition);
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        if (beanDefinition.isSingleton()) {
            addSingleton(beanName, bean);
        }
        return bean;
    }

    /**
     * 应用实例化之前的BeanPostProcessors
     */
    protected Object applyBeanPostProcessorsBeforeInstantiation(String beanName, Class<?> beanClass) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object bean = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if (bean != null)
                    return bean;
            }
        }
        return null;
    }

    /**
     * 依据策略实例化bean
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition) {
        return instantiationStrategy.instantiate(beanDefinition);
    }

    /**
     * 为bean填充属性
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        System.out.println("--------------> [ apply propertyValues ]");
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
    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        System.out.printf("--------------> [ initialize %s ]%n", beanName);

        if (bean instanceof BeanFactoryAware) {
            System.out.println("------------------> [ set beanFactory ]");
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }

        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception ex) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", ex);
        }

        return applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
    }

    /**
     * 执行BeanPostProcessor的前置处理
     */
    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        return applyBeanPostProcessor(existingBean, beanName, true);
    }

    /**
     * 执行BeanPostProcessor的前置处理
     */
    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        return applyBeanPostProcessor(existingBean, beanName, false);
    }

    /**
     * 执行初始化方法
     */
    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        System.out.println("------------------> [ invoke initMethods ]");

        boolean isInitializeBean = bean instanceof InitializingBean;
        if (isInitializeBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        String initMethodName = beanDefinition.getInitMethodName();
        // 避免同时继承自InitializingBean，且自定义方法与InitializingBean方法同名，初始化方法执行两次的情况
        if (StrUtil.isNotEmpty(initMethodName) &&
                !(isInitializeBean && "afterPropertiesSet".equals(initMethodName))) {
            Method initMethod = ClassUtil.getPublicMethod(beanDefinition.getBeanClass(), initMethodName);
            if (initMethod == null) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(bean);
        }
    }

    /**
     * 注册有销毁方法的bean（singleton类型的bean才需要注册），即bean继承自DisposableBean或有自定义的销毁方法
     */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        System.out.println("--------------> [ register DisposableBean if need ]");

        if (beanDefinition.isSingleton()) {
            if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
                registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
            }
        }
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

    /**
     * 获取实例化策略
     */
    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }
}
