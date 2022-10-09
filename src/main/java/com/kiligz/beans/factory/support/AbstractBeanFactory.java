package com.kiligz.beans.factory.support;

import com.kiligz.core.convert.ConversionService;
import com.kiligz.util.ClassUtil;
import com.kiligz.util.StringValueResolver;
import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.FactoryBean;
import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.beans.factory.config.BeanPostProcessor;
import com.kiligz.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象的bean工厂，提供获取bean的方法
 * 定义获取BeanDefinition方法、创建bean的方法
 *
 * @author Ivan
 * @date 2022/8/11 17:21
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    private final Map<String, Object> factoryBeanCache = new HashMap<>();

    private final List<StringValueResolver> embeddedValueResolverList = new ArrayList<>();

    private ConversionService conversionService;

    /**
     * 延迟加载，在使用bean时才加载创建bean，使用前以BeanDefinition保存对应信息
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        return doGetBean(beanName);
    }

    /**
     * 延迟加载，在使用bean时才加载创建bean，使用前以BeanDefinition保存对应信息，通过传递class对象确定返回类型
     */
    @Override
    public <T> T getBean(String beanName, Class<T> beanClass) throws BeansException {
        return beanClass.cast(doGetBean(beanName));
    }

    /**
     * 延迟加载，在使用bean时才加载创建bean，使用前以BeanDefinition保存对应信息，通过传递class对象确定返回类型
     * 首字母小写的class名称作为beanName
     */
    @Override
    public <T> T getBean(Class<T> beanClass) throws BeansException {
        String beanName = ClassUtil.getBeanNameFromClass(beanClass);
        return beanClass.cast(doGetBean(beanName));
    }

    /**
     * 是否包含bean
     */
    @Override
    public boolean containsBean(String beanName) {
        return containsBeanDefinition(beanName);
    }

    /**
     * 是否包含bean
     */
    @Override
    public <T> boolean containsBean(Class<T> beanClass) {
        return containsBeanDefinition(beanClass.getSimpleName());
    }

    /**
     * 延迟加载bean的实现
     */
    protected Object doGetBean(String beanName) {
        System.out.printf("-------> [ get bean: %s ]%n", beanName);

        Object sharedInstance = getSingleton(beanName);
        if (sharedInstance != null)
            return getObjectForBeanInstance(sharedInstance, beanName);

        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Object bean = createBean(beanName, beanDefinition);
        return getObjectForBeanInstance(bean, beanName);
    }

    /**
     * 是否包含BeanDefinition
     */
    protected abstract boolean containsBeanDefinition(String beanName);

    /**
     * 获取BeanDefinition，延迟实现
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 根据BeanDefinition创建bean，延迟实现
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

    /**
     * 添加允许修改或替换实例化后的bean的扩展，存在则覆盖
     */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessorList.remove(beanPostProcessor);
        beanPostProcessorList.add(beanPostProcessor);
    }

    /**
     * 添加嵌入式Value解析器
     */
    @Override
    public void addEmbeddedValueResolver(StringValueResolver resolver) {
        embeddedValueResolverList.add(resolver);
    }

    /**
     * 解析嵌入式Value
     */
    @Override
    public String resolveEmbeddedValue(String value) {
        for (StringValueResolver resolver : embeddedValueResolverList) {
            value = resolver.resolveStringValue(value);
        }
        return value;
    }

    /**
     * 注册类型转换服务
     */
    @Override
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /**
     * 获取类型转换服务
     */
    @Override
    public ConversionService getConversionService() {
        return conversionService;
    }

    /**
     * 获取所有BeanPostProcessor
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessorList;
    }

    /**
     * 如果是FactoryBean，从FactoryBean#getObject中创建bean
     */
    public Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        Object bean = beanInstance;
        if (beanInstance instanceof FactoryBean<?>) {
            System.out.println("----------> [ FactoryBean get object ]");

            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            try {
                if (factoryBean.isSingleton()) {
                    bean = factoryBeanCache.get(beanName);
                    if (bean == null) {
                        bean = factoryBean.getObject();
                    }
                } else {
                    bean = factoryBean.getObject();
                    factoryBeanCache.put(beanName, bean);
                }
            } catch (Exception e) {
                throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
            }
        }
        return bean;
    }
}
