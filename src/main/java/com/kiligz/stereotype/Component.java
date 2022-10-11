package com.kiligz.stereotype;

import java.lang.annotation.*;

/**
 * Component注解
 *
 * 包扫描时解析该注解
 * @see com.kiligz.context.annotation.ClassPathBeanDefinitionScanner#doScan(String...)
 *
 * @author Ivan
 * @date 2022/9/23 12:03
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
