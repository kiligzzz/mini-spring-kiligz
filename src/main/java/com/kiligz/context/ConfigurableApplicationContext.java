package com.kiligz.context;

import com.kiligz.beans.BeansException;

/**
 * 可配置的应用上下文
 *
 * @author Ivan
 * @date 2022/9/5 16:49
 */
public interface ConfigurableApplicationContext extends ApplicationContext {
    /**
     * 刷新容器
     */
    void refresh() throws BeansException;

    /**
     * 关闭应用上下文
     */
    void close();

    /**
     * 向虚拟机中注册一个钩子方法，在虚拟机关闭之前执行关闭容器等操作
     */
    void registerShutdownHook();
}
