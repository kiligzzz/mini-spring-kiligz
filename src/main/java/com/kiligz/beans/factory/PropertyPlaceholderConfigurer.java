package com.kiligz.beans.factory;

import cn.hutool.core.util.StrUtil;
import com.kiligz.context.support.AbstractApplicationContext;
import com.kiligz.util.LogUtil;
import com.kiligz.util.StringValueResolver;
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
import java.util.Properties;

/**
 * 占位符配置类
 *
 * beanFactory处理器，在refresh时，invokeBeanFactoryPostProcessors方法
 * bean实例化之前，执行该BeanFactoryPostProcessor
 * @see AbstractApplicationContext#refresh()
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
        LogUtil.replaceXmlPlaceHolderAndAddValueResolver();

        // 加载属性配置文件
        Properties properties = loadProperties();

        // 属性值替换占位符（对BeanDefinition操作）
        processProperties(beanFactory, properties);

        // 添加嵌入式Value解析器（bean实例化之后，设置属性之前，在自动装配注解BeanPostProcessor中执行）
        beanFactory.addEmbeddedValueResolver(new PlaceholderResolvingStringValueResolver(properties));
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
                value = resolvePlaceholder((String) value, properties);
                pvs.addPropertyValue(new PropertyValue(pv.getName(), value));
            }
        }
    }

    /**
     * 解析占位符
     */
    private static String resolvePlaceholder(String str, Properties properties) {
        // 占位符内容
        String content = StrUtil.subBetween(str, PLACEHOLDER_PREFIX, PLACEHOLDER_SUFFIX);
        if (content == null) return str;

        // 替换占位符的值
        String value = properties.getProperty(content);

        // 占位符整体
        String placeholder = PLACEHOLDER_PREFIX + content + PLACEHOLDER_SUFFIX;
        if (value == null)
            throw new BeansException(String.format(
                    "Could not resolve placeholder '%s' in value '%s'.", content, placeholder));

        // 返回替换后的结果
        return str.replace(placeholder, value);
    }

    /**
     * 占位符String值解析器
     */
    private static class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        public String resolveStringValue(String str) throws BeansException {
            return resolvePlaceholder(str, properties);
        }
    }
}
