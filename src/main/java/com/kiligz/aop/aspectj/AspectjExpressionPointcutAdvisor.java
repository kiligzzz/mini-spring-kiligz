package com.kiligz.aop.aspectj;

import com.kiligz.aop.Pointcut;
import com.kiligz.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * 支持aspectj表达式的切点顾问，切点和通知的组合
 *
 * @author Ivan
 * @date 2022/9/15 15:47
 */
public class AspectjExpressionPointcutAdvisor implements PointcutAdvisor {
    /**
     * 切点
     */
    private AspectjExpressionPointcut pointcut;

    /**
     * 通知
     */
    private Advice advice;

    /**
     * aspectj表达式
     */
    private String expression;

    public AspectjExpressionPointcutAdvisor() {
    }

    public AspectjExpressionPointcutAdvisor(String expression, Advice advice) {
        this.pointcut = new AspectjExpressionPointcut(expression);
        this.advice = advice;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        if (pointcut == null) {
            pointcut = new AspectjExpressionPointcut(expression);
        }
        return pointcut;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
