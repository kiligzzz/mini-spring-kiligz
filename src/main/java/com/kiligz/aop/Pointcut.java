package com.kiligz.aop;

/**
 * 切点接口
 *
 * @author Ivan
 * @date 2022/9/13 18:08
 */
public interface Pointcut {
    /**
     * 获取类匹配对象
     */
    ClassFilter getClassFilter();

    /**
     * 获取方法匹配对象
     */
    MethodMatcher getMethodMatcher();
}
