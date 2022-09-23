package com.kiligz.context.annotation;

import com.kiligz.beans.factory.config.BeanDefinition.BeanScope;

import java.lang.annotation.*;

/**
 * Scope注解
 *
 * @author Ivan
 * @date 2022/9/23 12:04
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {
    BeanScope value() default BeanScope.singleton;
}
