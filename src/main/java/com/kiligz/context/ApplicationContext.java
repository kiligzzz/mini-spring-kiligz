package com.kiligz.context;

import com.kiligz.beans.factory.HierarchicalBeanFactory;
import com.kiligz.beans.factory.ListableBeanFactory;
import com.kiligz.core.io.ResourceLoader;

/**
 * 应用上下文
 * - 比BeanFactory更为先进的IOC容器，拥有BeanFactory的所有功能
 * - 支持特殊类型bean，BeanFactoryPostProcessor和BeanPostProcessor的自动识别、
 *   资源加载、容器事件和监听器、国际化支持、单例bean自动初始化等。
 *
 * @author Ivan
 * @date 2022/9/5 16:48
 */
public interface ApplicationContext extends ApplicationEventPublisher, HierarchicalBeanFactory, ListableBeanFactory, ResourceLoader {
}
