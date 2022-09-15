package com.kiligz.test.aop;

import com.kiligz.aop.AdvisedSupport;
import com.kiligz.aop.TargetSource;
import com.kiligz.aop.aspectj.AspectjExpressionPointcut;
import com.kiligz.aop.framework.AopProxy;
import com.kiligz.aop.framework.CglibAopProxy;
import com.kiligz.aop.framework.ProxyFactory;
import com.kiligz.aop.framework.adapter.MethodBeforeAdviceInterceptor;

/**
 * @author Ivan
 * @date 2022/9/14 10:02
 */
public class TestAop {
    public static void main(String[] args) {
        // 方法匹配器
        AspectjExpressionPointcut methodMatcher =
                new AspectjExpressionPointcut("execution(* com.kiligz.test.aop.AopService.*(..))");

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTargetSource(new TargetSource(new AopService()));
        proxyFactory.setMethodMatcher(methodMatcher);
        proxyFactory.setMethodInterceptor(new MethodBeforeAdviceInterceptor(new AopServiceBefore()));

        AopService proxy = (AopService) proxyFactory.getProxy();
        proxy.printAop();
    }
}
