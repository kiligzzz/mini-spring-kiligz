package com.kiligz.aop.framework;

import com.kiligz.aop.AdvisedSupport;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 基于Cglib的动态代理
 *
 * @author Ivan
 * @date 2022/9/15 10:15
 */
public class CglibAopProxy implements AopProxy {
    private final AdvisedSupport advised;

    public CglibAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advised.getTargetSource().getTarget().getClass());
        enhancer.setInterfaces(advised.getTargetSource().getTargetClass());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advised));
        return enhancer.create();
    }

    /**
     * 此处的MethodInterceptor是cglib中的接口，advised中的MethodInterceptor的AOP联盟中定义的接口，定义此类做适配
     */
    private static class DynamicAdvisedInterceptor implements MethodInterceptor {
        private final AdvisedSupport advised;

        private DynamicAdvisedInterceptor(AdvisedSupport advised) {
            this.advised = advised;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            Object target = advised.getTargetSource().getTarget();
            CglibMethodInvocation methodInvocation = new CglibMethodInvocation(target, method, objects, methodProxy);
            if (advised.getMethodMatcher().matches(method, target.getClass())) {
                return advised.getMethodInterceptor().invoke(methodInvocation);
            }
            return methodInvocation.proceed();
        }
    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy methodProxy) {
            super(target, method, arguments);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return methodProxy.invoke(target, arguments);
        }
    }

}
