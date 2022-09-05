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
}
