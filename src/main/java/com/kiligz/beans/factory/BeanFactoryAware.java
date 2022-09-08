package com.kiligz.beans.factory;

import com.kiligz.beans.BeansException;

/**
 * 实现该接口，能感知所属BeanFactory
 *
 * @author Ivan
 * @date 2022/9/8 09:46
 */
public interface BeanFactoryAware extends Aware {

    /**
     * 设置beanFactory
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
