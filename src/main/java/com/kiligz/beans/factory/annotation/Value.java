package com.kiligz.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * Value注解
 *
 * @author Ivan
 * @date 2022/9/26 17:17
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {
    String value();
}
