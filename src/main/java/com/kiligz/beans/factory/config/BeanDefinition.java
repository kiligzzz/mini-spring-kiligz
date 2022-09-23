package com.kiligz.beans.factory.config;

import cn.hutool.core.util.StrUtil;
import com.kiligz.beans.PropertyValues;

/**
 * 定义bean信息的类
 * 包含bean的class类型、构造参数、属性值等信息，每个bean对应一个
 *
 * @author Ivan
 * @date 2022/8/11 17:18
 */
public class BeanDefinition {
    /**
     * Bean的类
     */
    private Class<?> beanClass;

    /**
     * Bean的属性内容
     */
    private PropertyValues propertyValues;

    /**
     * 初始化方法名
     */
    private String initMethodName;

    /**
     * 销毁方法名
     */
    private String destroyMethodName;

    /**
     * bean域，默认是singleton
     */
    private BeanScope beanScope;

    public BeanDefinition(Class<?> beanClass) {
        this(beanClass, null);
    }

    public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues == null ?
                new PropertyValues() : propertyValues;
        this.beanScope = BeanScope.singleton;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    public void setBeanScope(String beanScope) {
        if (StrUtil.isNotEmpty(beanScope)) {
            setBeanScope(BeanScope.valueOf(beanScope));
        }
    }

    public void setBeanScope(BeanScope beanScope) {
        this.beanScope = beanScope;
    }

    public boolean isSingleton() {
        return beanScope == BeanScope.singleton;
    }

    public boolean isPrototype() {
        return beanScope == BeanScope.prototype;
    }

    public enum BeanScope {
        singleton,
        prototype
    }
}
