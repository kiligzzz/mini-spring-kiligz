package com.kiligz.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.kiligz.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.beans.factory.support.BeanDefinitionRegistry;
import com.kiligz.stereotype.Component;

import java.util.Set;

/**
 * 类路径BeanDefinition扫描器
 *
 * @author Ivan
 * @date 2022/9/23 12:11
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    /**
     * 自动装配注解处理器的bean名称
     */
    public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME =
            StrUtil.lowerFirst(AutowiredAnnotationBeanPostProcessor.class.getSimpleName());

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
                registry.registerBeanDefinitionWithNoRepeated(beanName, candidate);
            }
        }

        // 注册自动装配注解处理器的BeanDefinition
        registry.registerBeanDefinitionWithNoRepeated(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME,
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
            return StrUtil.lowerFirst(beanClass.getSimpleName());
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