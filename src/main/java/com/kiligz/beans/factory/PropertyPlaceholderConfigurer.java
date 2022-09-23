package com.kiligz.beans.factory;

import cn.hutool.core.util.StrUtil;
import com.kiligz.beans.BeansException;
import com.kiligz.beans.PropertyValue;
import com.kiligz.beans.PropertyValues;
import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.beans.factory.config.BeanFactoryPostProcessor;
import com.kiligz.core.io.DefaultResourceLoader;
import com.kiligz.core.io.Resource;
import com.kiligz.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

/**
 * Xml占位符配置类
 *
 * @author Ivan
 * @date 2022/9/23 10:15
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final String PLACEHOLDER_PREFIX = "${";

    public static final String PLACEHOLDER_SUFFIX = "}";

    private String location;

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 加载属性配置文件
        Properties properties = loadProperties();

        // 属性值替换占位符
        processProperties(beanFactory, properties);
    }

    /**
     * 加载属性配置文件
     */
    private Properties loadProperties() {
        try {
            ResourceLoader loader = new DefaultResourceLoader();
            Resource resource = loader.getResource(location);
            InputStream is = resource.getInputStream();

            Properties properties = new Properties();
            properties.load(is);
            return properties;
        } catch (IOException e) {
            throw new BeansException("load " + location + " properties error.", e);
        }
    }

    /**
     * 属性值替换占位符
     */
    private void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties) {
        String[] beanNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            resolvePropertyValues(beanDefinition, properties);
        }
    }

    /**
     * 替换beanDefinition的占位符
     */
    private void resolvePropertyValues(BeanDefinition beanDefinition, Properties properties) {
        PropertyValues pvs = beanDefinition.getPropertyValues();
        for (PropertyValue pv : pvs.getPropertyValues()) {
            Object value = pv.getValue();
            if (value instanceof String) {
                String str = (String) value;
                String replaceFrom = StrUtil.subBetween(str, PLACEHOLDER_PREFIX, PLACEHOLDER_SUFFIX);
                String replaceTo = Optional.ofNullable(replaceFrom)
                        .map(properties::getProperty)
                        .orElse(null);
                if (replaceTo != null) {
                    str = str.replace(PLACEHOLDER_PREFIX + replaceFrom + PLACEHOLDER_SUFFIX,
                            replaceTo);
                    pvs.addPropertyValue(new PropertyValue(pv.getName(), str));
                }
            }
        }
    }
}
