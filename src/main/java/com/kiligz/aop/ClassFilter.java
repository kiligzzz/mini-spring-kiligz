package com.kiligz.aop;

/**
 * 类匹配接口
 *
 * @author Ivan
 * @date 2022/9/13 18:08
 */
public interface ClassFilter {
    /**
     * 是否匹配
     */
    boolean matches(Class<?> clazz);
}
