package com.kiligz.aop.framework;

import com.kiligz.aop.AdvisedSupport;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 基于Jdk的动态代理
 *
 * @author Ivan
 * @date 2022/9/14 10:18
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    /**
     * 获取代理对象
     */
    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(getClass().getClassLoader(), advised.getTargetSource().getTargetClass(), this);
    }

    /**
     * 方法执行
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object target = advised.getTargetSource().getTarget();
        if (advised.getMethodMatcher().matches(method, target.getClass())) {
            // 代理方法
            MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(target, method, args));
        }
        return method.invoke(target, args);
    }
}
