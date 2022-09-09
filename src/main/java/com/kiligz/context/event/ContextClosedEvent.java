package com.kiligz.context.event;

/**
 * 上下文（容器）关闭事件
 *
 * @author Ivan
 * @date 2022/9/9 17:04
 */
public class ContextClosedEvent extends ApplicationContextEvent {
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
