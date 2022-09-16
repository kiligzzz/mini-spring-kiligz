package com.kiligz.aop;

/**
 * 由切入点驱动的顾问接口
 *
 * @author Ivan
 * @date 2022/9/15 15:29
 */
public interface PointcutAdvisor extends Advisor {
    /**
     * 获取切点
     */
    Pointcut getPointcut();
}
