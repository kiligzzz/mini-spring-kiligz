package com.kiligz.aop;

import org.aopalliance.aop.Advice;

/**
 * 顾问接口，为了支持不同类型的通知的通用性
 *
 * @author Ivan
 * @date 2022/9/15 15:29
 */
public interface Advisor {
    /**
     * 获取通知
     */
    Advice getAdvice();
}
