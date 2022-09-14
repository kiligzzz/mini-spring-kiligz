package com.kiligz.test.aop;

import com.kiligz.aop.AdvisedSupport;
import com.kiligz.aop.TargetSource;
import com.kiligz.aop.aspectj.AspectjExpressionPointcut;
import com.kiligz.aop.framework.AopProxy;
import com.kiligz.aop.framework.JdkDynamicAopProxy;

/**
 * @author Ivan
 * @date 2022/9/14 10:02
 */
public class TestAop {
    public static void main(String[] args) {
        // 方法匹配器
        AspectjExpressionPointcut methodMatcher =
                new AspectjExpressionPointcut("execution(* com.kiligz.test.aop.AopService.*(..))");

        AdvisedSupport advised = new AdvisedSupport();
        advised.setTargetSource(new TargetSource(new AopServiceImpl()));
        advised.setMethodMatcher(methodMatcher);
        advised.setMethodInterceptor(new AopServiceInterceptor());

        AopProxy aopProxy = new JdkDynamicAopProxy(advised);
        AopService proxy = (AopService) aopProxy.getProxy();
        proxy.printAop();
    }
}
