package com.kiligz.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 类路径扫描候选组件提供者
 *
 * @author Ivan
 * @date 2022/9/23 12:12
 */
public class ClassPathScanningCandidateComponentProvider {

    /**
     * 找到候选的组件
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new HashSet<>();
        // 扫描有Component注解的类
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            candidates.add(beanDefinition);
        }
        return candidates;
    }
}
