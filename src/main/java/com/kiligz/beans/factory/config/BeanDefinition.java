package com.kiligz.beans.factory.config;

import com.kiligz.beans.factory.PropertyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义bean信息的类
 * 包含bean的class类型、构造参数、属性值等信息，每个bean对应一个
 *
 * @author Ivan
 * @date 2022/8/11 17:18
 */
public class BeanDefinition {
    private Class<?> beanClass;

    private List<PropertyValue> propertyValueList = new ArrayList<>();

    public BeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public BeanDefinition(Class<?> beanClass, List<PropertyValue> propertyValueList) {
        this.beanClass = beanClass;
        this.propertyValueList = propertyValueList;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public List<PropertyValue> getPropertyValueList() {
        return propertyValueList;
    }

    public void setPropertyValueList(List<PropertyValue> propertyValueList) {
        this.propertyValueList = propertyValueList;
    }
}
