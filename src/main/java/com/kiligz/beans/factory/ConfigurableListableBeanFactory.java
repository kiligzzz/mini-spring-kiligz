package com.kiligz.beans.factory;

import com.kiligz.beans.factory.config.AutowireCapableBeanFactory;
import com.kiligz.beans.factory.config.ConfigurableBeanFactory;

/**
 * 可配置的、可列出的bean工厂接口
 *
 * @author Ivan
 * @date 2022/8/22 11:02
 */
public interface ConfigurableListableBeanFactory extends AutowireCapableBeanFactory,
        ConfigurableBeanFactory, ListableBeanFactory {
}
