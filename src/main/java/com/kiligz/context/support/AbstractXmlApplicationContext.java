package com.kiligz.context.support;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.support.BeanDefinitionReader;
import com.kiligz.beans.factory.support.DefaultListableBeanFactory;
import com.kiligz.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 抽象的xml应用上下文
 *
 * @author Ivan
 * @date 2022/9/5 16:52
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {
    /**
     * 加载BeanDefinition
     */
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException {
        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    /**
     * 获取xml文件的位置
     */
    protected abstract String[] getConfigLocations();
}
