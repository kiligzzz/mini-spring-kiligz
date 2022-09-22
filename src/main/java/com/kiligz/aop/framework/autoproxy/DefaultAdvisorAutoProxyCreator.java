package com.kiligz.aop.framework.autoproxy;

import com.kiligz.aop.Advisor;
import com.kiligz.aop.ClassFilter;
import com.kiligz.aop.Pointcut;
import com.kiligz.aop.TargetSource;
import com.kiligz.aop.aspectj.AspectjExpressionPointcutAdvisor;
import com.kiligz.aop.framework.ProxyFactory;
import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.BeanFactory;
import com.kiligz.beans.factory.BeanFactoryAware;
import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.kiligz.beans.factory.support.DefaultListableBeanFactory;
import com.kiligz.beans.factory.support.SimpleInstantiationStrategy;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;

/**
 * 默认的顾问自动代理创建者
 * 在bean实例化之前执行创建代理对象返回
 *
 * @author Ivan
 * @date 2022/9/19 10:23
 */
public class DefaultAdvisorAutoProxyCreator implements BeanFactoryAware, InstantiationAwareBeanPostProcessor {
    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        // 避免死循环，利用advisor生成代理对象，advisor就不需要了
        if (isInfrastructureClass(beanClass)) {
            return null;
        }

        System.out.println("------------------> [ postProcess before instantiation (autoProxy) ]");

        Collection<AspectjExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectjExpressionPointcutAdvisor.class).values();
        try {
            for (AspectjExpressionPointcutAdvisor advisor : advisors) {
                ClassFilter classFilter = advisor.getPointcut().getClassFilter();
                if (classFilter.matches(beanClass)) {
                    // 实例化bean构造被代理对象的封装
                    BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                    // 不用代理创建bean，否则代理两次有问题
                    Object bean = new SimpleInstantiationStrategy().instantiate(beanDefinition);
                    TargetSource targetSource = new TargetSource(bean);

                    ProxyFactory proxyFactory = new ProxyFactory();
                    proxyFactory.setTargetSource(targetSource);
                    proxyFactory.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
                    proxyFactory.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());

                    // 返回代理对象
                    return proxyFactory.getProxy();
                }
            }
        } catch (Exception e) {
            throw new BeansException("Error create proxy bean for: " + beanName, e);
        }
        return null;
    }

    /**
     * 是否是Advice、Pointcut、Advisor的下层结构
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || Pointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
