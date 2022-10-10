package com.kiligz.aop.framework;

import com.kiligz.aop.AdvisedSupport;
import com.kiligz.util.LogUtil;

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
        String beanName = getTargetSource().getTarget().getClass().getSimpleName();
        if (getTargetSource().getTargetClass().length > 0) {
            LogUtil.jdkAop(beanName);

            return new JdkDynamicAopProxy(this);
        }
        LogUtil.cglibAop(beanName);

        return new CglibAopProxy(this);
    }
}
