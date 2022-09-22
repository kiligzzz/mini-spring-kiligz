package com.kiligz.context.event;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.BeanFactory;
import com.kiligz.beans.factory.BeanFactoryAware;
import com.kiligz.context.ApplicationEvent;
import com.kiligz.context.ApplicationListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

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
    protected final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();
    
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

    /**
     * 获取符合事件类型的、对该事件感兴趣的监听器
     */
    protected List<ApplicationListener<ApplicationEvent>> getApplicationEventListeners(ApplicationEvent event) {
        List<ApplicationListener<ApplicationEvent>> listenerList = new ArrayList<>();
        for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if (supportsEvent(listener, event)) {
                listenerList.add(listener);
            }
        }
        return listenerList;
    }

    /**
     * 监听器是否对该事件感兴趣
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        Class<?> listenerClass = applicationListener.getClass();

        // 若经过实例化cglib代理则获取其superClass为原始类
        Class<?> targetClass = isCglibProxyClass(listenerClass) ?
                listenerClass.getSuperclass() : listenerClass;

        Type genericInterface = targetClass.getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }
        return eventClassName.isAssignableFrom(event.getClass());
    }

    /**
     * 是否经过cglib代理了
     */
    protected boolean isCglibProxyClass(Class<?> listenerClass) {
        return listenerClass != null && listenerClass.getName().contains("$$");
    }
}
