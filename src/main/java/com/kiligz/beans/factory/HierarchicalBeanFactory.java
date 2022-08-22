package com.kiligz.beans.factory;

/**
 * 分层bean工厂接口，分层思想
 * 当获取Bean对象时，如果当前BeanFactory中不存在对应的bean，则会访问其直接parentBeanFactory以尝试获取Bean对象
 *
 * @author Ivan
 * @date 2022/8/22 11:00
 */
public interface HierarchicalBeanFactory extends BeanFactory {
}
