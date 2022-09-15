package com.kiligz.aop.framework;

import com.kiligz.aop.AdvisedSupport;

/**
 * Aop代理工厂，根据有无接口自动选择jdk或cglib动态代理
 *
 * @author Ivan
 * @date 2022/9/15 10:41
 */
public class ProxyFactory extends AdvisedSupport {
    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        if (getTargetSource().getTargetClass().length > 0) {
            System.out.println("[ jdk aop ]");
            return new JdkDynamicAopProxy(this);
        }
        System.out.println("[ cglib aop ]");
        return new CglibAopProxy(this);
    }
}
