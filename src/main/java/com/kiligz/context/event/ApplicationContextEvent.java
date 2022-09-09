package com.kiligz.context.event;

import com.kiligz.context.ApplicationContext;
import com.kiligz.context.ApplicationEvent;

/**
 * 抽象的应用上下文（容器）刷新事件
 *
 * @author Ivan
 * @date 2022/9/9 17:01
 */
public abstract class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * 获取应用上下文
     */
    public ApplicationContext getApplicationContext() {
        return (ApplicationContext) source;
    }
}
