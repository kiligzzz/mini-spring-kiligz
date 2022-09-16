package com.kiligz.test.aop;

import com.kiligz.aop.TargetSource;
import com.kiligz.aop.aspectj.AspectjExpressionPointcut;
import com.kiligz.aop.aspectj.AspectjExpressionPointcutAdvisor;
import com.kiligz.aop.framework.ProxyFactory;
import com.kiligz.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author Ivan
 * @date 2022/9/14 10:02
 */
public class TestAop {
    public static void main(String[] args) {
        // 支持aspectj表达式的切点顾问，切点和通知的组合
        AspectjExpressionPointcutAdvisor advisor =
                new AspectjExpressionPointcutAdvisor();
        advisor.setExpression("execution(* com.kiligz.test.aop.AopService.*(..))");
        advisor.setAdvice(new MethodBeforeAdviceInterceptor(new AopServiceBefore()));

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTargetSource(new TargetSource(new AopService()));
        proxyFactory.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
        proxyFactory.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

        AopService proxy = (AopService) proxyFactory.getProxy();
        proxy.printAop();
    }
}
