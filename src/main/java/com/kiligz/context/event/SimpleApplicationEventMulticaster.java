package com.kiligz.context.event;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.BeanFactory;
import com.kiligz.context.ApplicationEvent;
import com.kiligz.context.ApplicationListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 应用事件广播器
 *
 * @author Ivan
 * @date 2022/9/9 17:32
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {
    /**
     * 设置beanFactory
     */
    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    /**
     * 广播事件
     */
    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if (supportsEvent(listener, event)) {
                listener.onApplicationEvent(event);
            }
        }
    }

    /**
     * 监听器是否对该事件感兴趣
     */
    protected boolean supportsEvent(ApplicationListener<?> listener, ApplicationEvent event) {
        Type type = listener.getClass().getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }
        return eventClassName.isAssignableFrom(event.getClass());
    }
}
