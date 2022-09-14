package com.kiligz.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配接口
 *
 * @author Ivan
 * @date 2022/9/13 18:09
 */
public interface MethodMatcher {
    /**
     * 是否匹配
     */
    boolean matches(Method method, Class<?> targetClass);
}
