package com.kiligz.beans.factory.support;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * 使用Cglib动态生成子类（默认）
 *
 * @author Ivan
 * @date 2022/8/13 16:19
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {
    
    /**
     * 使用Cglib动态生成子类
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition, Constructor<?> ctor, Object[] args) throws BeansException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(NoOp.INSTANCE);
        return ctor == null ?
                enhancer.create() :
                enhancer.create(ctor.getParameterTypes(), args);
    }
}
