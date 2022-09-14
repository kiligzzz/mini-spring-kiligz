package com.kiligz.aop;

/**
 * 被代理对象的封装
 *
 * @author Ivan
 * @date 2022/9/14 10:16
 */
public class TargetSource {
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public Object getTarget() {
        return target;
    }

    public Class<?>[] getTargetClass() {
        return target.getClass().getInterfaces();
    }
}
