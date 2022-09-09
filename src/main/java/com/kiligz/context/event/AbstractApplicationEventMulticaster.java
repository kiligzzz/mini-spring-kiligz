package com.kiligz.context.event;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.BeanFactory;
import com.kiligz.beans.factory.BeanFactoryAware;
import com.kiligz.context.ApplicationEvent;
import com.kiligz.context.ApplicationListener;

import java.util.HashSet;
import java.util.Set;

/**
 * 抽象的应用事件广播器
 *
 * @author Ivan
 * @date 2022/9/9 17:21
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {
    /**
     * 应用监听器集合
     */
    protected final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new HashSet<>();
    
    private BeanFactory beanFactory;

    @Override
    public void addApplicationListener(ApplicationListener<ApplicationEvent> listener) {
        applicationListeners.add(listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<ApplicationEvent> listener) {
        applicationListeners.remove(listener);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
