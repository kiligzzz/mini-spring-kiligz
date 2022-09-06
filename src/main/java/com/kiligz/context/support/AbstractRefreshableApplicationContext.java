package com.kiligz.context.support;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.support.DefaultListableBeanFactory;

/**
 * 抽象的可刷新的应用上下文
 *
 * @author Ivan
 * @date 2022/9/5 16:52
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
    /**
     * 默认的beanFactory
     */
    private DefaultListableBeanFactory beanFactory;

    /**
     * 获取beanFactory
     */
    public DefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    /**
     * 创建beanFactory并加载BeanDefinition
     */
    protected final void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    /**
     * 创建bean工厂
     */
    protected DefaultListableBeanFactory createBeanFactory() {
        System.out.println("---> [ create defaultListableBeanFactory ] ");
        return new DefaultListableBeanFactory();
    }

    /**
     * 加载BeanDefinition
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException;
}
