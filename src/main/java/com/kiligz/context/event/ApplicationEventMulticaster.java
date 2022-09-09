package com.kiligz.context.event;

import com.kiligz.context.ApplicationEvent;
import com.kiligz.context.ApplicationListener;

/**
 * 应用事件广播器
 *
 * @author Ivan
 * @date 2022/9/9 17:07
 */
public interface ApplicationEventMulticaster {
    /**
     * 添加事件监听器
     */
    void addApplicationListener(ApplicationListener<ApplicationEvent> listener);

    /**
     * 删除应用监听器
     */
    void removeApplicationListener(ApplicationListener<ApplicationEvent> listener);

    /**
     * 广播事件
     */
    void multicastEvent(ApplicationEvent event);
}
