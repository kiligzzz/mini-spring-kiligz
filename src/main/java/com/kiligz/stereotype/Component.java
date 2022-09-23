package com.kiligz.stereotype;

import java.lang.annotation.*;

/**
 * Component注解
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
