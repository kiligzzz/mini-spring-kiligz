package com.kiligz.context.event;

/**
 * 上下文（容器）刷新事件
 *
 * @author Ivan
 * @date 2022/9/9 16:59
 */
public class ContextRefreshedEvent extends ApplicationContextEvent {
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
