package com.kiligz.beans.factory.config;

import com.kiligz.beans.factory.HierarchicalBeanFactory;

/**
 * 可配置bean工厂接口
 *
 * @author Ivan
 * @date 2022/8/22 11:01
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
}
