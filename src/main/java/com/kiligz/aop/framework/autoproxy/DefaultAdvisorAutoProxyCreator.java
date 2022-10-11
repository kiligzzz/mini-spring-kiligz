package com.kiligz.aop.framework.autoproxy;

import com.kiligz.aop.Advisor;
import com.kiligz.aop.ClassFilter;
import com.kiligz.aop.Pointcut;
import com.kiligz.aop.TargetSource;
import com.kiligz.aop.aspectj.AspectjExpressionPointcutAdvisor;
import com.kiligz.aop.framework.ProxyFactory;
import com.kiligz.beans.BeansException;
import com.kiligz.beans.PropertyValues;
import com.kiligz.beans.factory.BeanFactory;
import com.kiligz.beans.factory.BeanFactoryAware;
import com.kiligz.beans.factory.ConfigurableListableBeanFactory;
import com.kiligz.beans.factory.config.BeanDefinition;
import com.kiligz.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.kiligz.beans.factory.support.DefaultListableBeanFactory;
import com.kiligz.beans.factory.support.SimpleInstantiationStrategy;
import com.kiligz.stereotype.Component;
import com.kiligz.util.LogUtil;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 默认的顾问自动代理创建者
 * 在bean初始化之后创建代理对象返回
 *
 * @author Ivan
 * @date 2022/9/19 10:23
 */
@Component
public class DefaultAdvisorAutoProxyCreator implements BeanFactoryAware, InstantiationAwareBeanPostProcessor {
    private ConfigurableListableBeanFactory beanFactory;

    private final Set<Object> earlyProxyReferences = new HashSet<>();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!earlyProxyReferences.contains(beanName)) {
            LogUtil.postProcessAfterInitialization();

            return wrapIfNecessary(beanName, bean);
        }
        return bean;
    }

    @Override
    public Object getEarlyBeanReference(String beanName, Object bean) {
        LogUtil.getEarlyBeanReference(beanName);

        earlyProxyReferences.add(beanName);
        return wrapIfNecessary(beanName, bean);
    }

    /**
     * 包装如果有必要
     */
    private Object wrapIfNecessary(String beanName, Object bean) {
        // 避免死循环，利用advisor生成代理对象，advisor就不需要了
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }

        Collection<AspectjExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectjExpressionPointcutAdvisor.class).values();
        try {
            for (AspectjExpressionPointcutAdvisor advisor : advisors) {
                ClassFilter classFilter = advisor.getPointcut().getClassFilter();
                if (classFilter.matches(bean.getClass())) {
                    // 实例化bean构造被代理对象的封装
                    BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                    // 不用代理创建bean，否则代理两次有问题
                    Object noProxyBean = new SimpleInstantiationStrategy().instantiate(beanDefinition);
                    TargetSource targetSource = new TargetSource(noProxyBean);

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
        return bean;
    }

    /**
     * 是否是Advice、Pointcut、Advisor的下层结构
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || Pointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }
}
