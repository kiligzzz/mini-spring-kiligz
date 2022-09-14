package com.kiligz.test.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Ivan
 * @date 2022/9/14 10:40
 */
public class AopServiceInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("[ aop before ]");
        Object result = methodInvocation.proceed();
        System.out.println("[ aop after ]");
        return result;
    }
}
