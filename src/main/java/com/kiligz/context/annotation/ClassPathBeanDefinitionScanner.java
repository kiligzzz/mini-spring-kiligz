package com.kiligz.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.kiligz.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.beans.factory.support.BeanDefinitionRegistry;
import com.kiligz.core.io.Resource;
import com.kiligz.stereotype.Component;
import com.kiligz.util.ClassUtil;
import com.kiligz.util.LogUtil;

import java.util.Set;

/**
 * 类路径BeanDefinition扫描器
 *
 * 装载BeanDefinition的扫描包时
 * @see com.kiligz.beans.factory.xml.XmlBeanDefinitionReader#loadBeanDefinitions(Resource)
 *
 * @author Ivan
 * @date 2022/9/23 12:11
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    /**
     * 自动装配注解处理器的bean名称
     */
    public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME =
            ClassUtil.getBeanNameFromClass(AutowiredAnnotationBeanPostProcessor.class);

    private final BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 扫描指定包下的注解
     */
    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                resolveAnSetBeanScope(candidate);

                String beanName = resolveBeanName(candidate);
                registry.registerBeanDefinitionWithoutRepeated(beanName, candidate);
            }
        }

        // 注册自动装配注解处理器的BeanDefinition
        LogUtil.registerAutowiredAnnotationProcessor();
        registry.registerBeanDefinitionWithoutRepeated(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME,
                new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    /**
     * 解析beanName，如果设置了使用设置的，没有设置使用首字母小写的class
     */
    private String resolveBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (value.isEmpty()) {
            return ClassUtil.getBeanNameFromClass(beanClass);
        }
        return value;
    }

    /**
     * 解析并设置bean的作用域
     */
    private void resolveAnSetBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (scope != null) {
            beanDefinition.setBeanScope(scope.value());
        }
    }
}
