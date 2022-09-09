package com.kiligz.context;

/**
 * 事件发布者接口
 *
 * @author Ivan
 * @date 2022/9/9 16:54
 */
public interface ApplicationEventPublisher {
    /**
     * 发布事件
     */
    void publishEvent(ApplicationEvent event);
}
