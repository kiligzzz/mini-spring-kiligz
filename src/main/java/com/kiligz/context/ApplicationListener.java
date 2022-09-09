package com.kiligz.context;

import java.util.EventListener;

/**
 * 应用监听器接口
 *
 * @author Ivan
 * @date 2022/9/9 17:13
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    /**
     * 处理应用事件，接收ApplicationEvent的子类实例
     */
    void onApplicationEvent(E event);
}
