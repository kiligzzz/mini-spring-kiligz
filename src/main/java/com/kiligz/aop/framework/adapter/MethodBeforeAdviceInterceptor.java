package com.kiligz.aop.framework.adapter;

import com.kiligz.aop.BeforeAdvice;
import com.kiligz.aop.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 前置通知拦截器
 *
 * @author Ivan
 * @date 2022/9/15 14:28
 */
public class MethodBeforeAdviceInterceptor implements BeforeAdvice, MethodInterceptor {
    private final MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        advice.before(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return methodInvocation.proceed();
    }
}
