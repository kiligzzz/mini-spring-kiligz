package com.kiligz.context.event;

import com.kiligz.beans.factory.BeanFactory;
import com.kiligz.context.ApplicationEvent;
import com.kiligz.context.ApplicationListener;

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
        for (ApplicationListener<ApplicationEvent> listener : getApplicationEventListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
