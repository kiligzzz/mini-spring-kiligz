package com.kiligz.aop;

import java.lang.reflect.Method;

/**
 * 方法前置通知接口，定义before方法
 *
 * @author Ivan
 * @date 2022/9/15 14:27
 */
public interface MethodBeforeAdvice extends BeforeAdvice {
    /**
     * 方法执行前执行
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
