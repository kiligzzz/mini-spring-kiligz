package com.kiligz.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import com.kiligz.beans.BeansException;
import com.kiligz.beans.PropertyValue;
import com.kiligz.beans.factory.BeanFactoryAware;
import com.kiligz.beans.factory.DisposableBean;
import com.kiligz.beans.factory.InitializingBean;
import com.kiligz.beans.factory.config.*;
import com.kiligz.util.ConvertUtil;
import com.kiligz.util.LogUtil;

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
        LogUtil.createBean(beanName);
        
        // 如果bean实例化之前需要处理，则直接返回处理后对象，不放入singletonObjects中
        Object bean = resolveBeforeInstantiation(beanName, beanDefinition);
        if (bean != null)
            return bean;
        
        return doCreateBean(beanName, beanDefinition);
    }

    /**
     * 在实例化之前判断是否需要处理（自定义bean不需要自动代理，也就会放入singletonObjects）
     * 执行InstantiationAwareBeanPostProcessor的方法
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
        // 依据策略实例化bean
        Object bean = createBeanInstance(beanDefinition);

        // 处理循环依赖，将实例化后的Bean对象提前放入缓存中暴露出来
        if (beanDefinition.isSingleton()) {
            Object finalBean = bean;
            addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, finalBean));
        }

        // 应用实例化之后的BeanPostProcessors，任意一个返回false，则不继续逻辑
        boolean continueLogic = applyBeanPostProcessorsAfterInstantiation(beanName, bean);
        if (!continueLogic)
            return bean;

        // 实例化后，为bean设置属性前，应用BeanPostProcessors（@Value、@Autowired注解修改PropertyValues值）
        applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);

        // 为bean填充属性
        applyPropertyValues(beanName, bean, beanDefinition);

        // 初始化bean，执行前置处理，执行初始化方法，执行后置处理
        bean = initializeBean(beanName, bean, beanDefinition);

        // 注册有销毁方法的bean（singleton类型的bean才需要注册），即bean继承自DisposableBean或有自定义的销毁方法
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        // 缓存bean
        Object exposedObject = bean;
        if (beanDefinition.isSingleton()) {
            exposedObject = getSingleton(beanName);
            addSingleton(beanName, exposedObject);
        }
        return exposedObject;
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
     * 获取早期的bean的依赖
     */
    protected Object getEarlyBeanReference(String beanName, Object bean) {
        Object exposedObject = bean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                exposedObject = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).getEarlyBeanReference(beanName, exposedObject);
                if (exposedObject == null)
                    break;
            }
        }
        return exposedObject;
    }

    /**
     * 应用实例化之后的BeanPostProcessors，任意一个返回false，则不继续逻辑
     */
    protected boolean applyBeanPostProcessorsAfterInstantiation(String beanName, Object bean) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                boolean continueLogic = ((InstantiationAwareBeanPostProcessor) beanPostProcessor)
                        .postProcessAfterInstantiation(bean, beanName);
                if (!continueLogic)
                    return false;
            }
        }
        return true;
    }

    /**
     * 实例化后，为bean设置属性前，应用BeanPostProcessors（@Value、@Autowired注解修改PropertyValues值）
     */
    protected void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                 ((InstantiationAwareBeanPostProcessor) beanPostProcessor)
                        .postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
            }
        }
    }

    /**
     * 为bean填充属性
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        LogUtil.applyPropertyValues();
        try {
            for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                if (value instanceof BeanReference) {
                    // beanA依赖beanB，先实例化beanB
                    BeanReference beanReference = (BeanReference) value;
                    LogUtil.refBean(beanReference.getBeanName());

                    value = getBean(beanReference.getBeanName());
                } else {
                    // 类型转换
                    value = ConvertUtil.convert(
                            getConversionService(), value, TypeUtil.getFieldType(bean.getClass(), name));
                }

                // 属性填充
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values：" + beanName);
        }
    }

    /**
     * 初始化bean，执行前置处理，执行初始化方法，执行后置处理
     */
    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        LogUtil.initializeBean(beanName);

        if (bean instanceof BeanFactoryAware) {
            LogUtil.setBeanFactory();

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
        return applyBeanPostProcessorWithInitialization(existingBean, beanName, true);
    }

    /**
     * 执行BeanPostProcessor的后置处理（如使用pointcutAdvisor为其生成代理对象）
     */
    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        return applyBeanPostProcessorWithInitialization(existingBean, beanName, false);
    }

    /**
     * 执行初始化方法
     */
    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        LogUtil.invokeInitMethods();

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
        if (beanDefinition.isSingleton()) {
            if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
                registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
            }
        }
    }

    /**
     * 执行BeanPostProcessors的方法
     */
    protected Object applyBeanPostProcessorWithInitialization(Object existingBean, String beanName, boolean isBefore) {
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
