package com.kiligz.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * 代理支持类，存储被代理对象的封装、方法匹配器、方法拦截器
 *
 * @author Ivan
 * @date 2022/9/14 10:20
 */
public class AdvisedSupport {
    private TargetSource targetSource;

    private MethodMatcher methodMatcher;

    private MethodInterceptor methodInterceptor;

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }
}
