package com.kiligz.test.aop;

import com.kiligz.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author Ivan
 * @date 2022/9/15 14:37
 */
public class AopServiceBefore implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("[ before advice ]");
    }
}
