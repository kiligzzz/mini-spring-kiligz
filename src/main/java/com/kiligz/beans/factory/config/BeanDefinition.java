package com.kiligz.beans.factory.config;

import com.kiligz.beans.factory.PropertyValues;

/**
 * 定义bean信息的类
 * 包含bean的class类型、构造参数、属性值等信息，每个bean对应一个
 *
 * @author Ivan
 * @date 2022/8/11 17:18
 */
public class BeanDefinition {
    private Class<?> beanClass;

    private PropertyValues propertyValues;

    public BeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues == null ?
                new PropertyValues() : propertyValues;
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
}
