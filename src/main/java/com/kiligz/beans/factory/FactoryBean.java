package com.kiligz.beans.factory;

/**
 * FactoryBean是一种特殊的bean
 * 当向容器获取该bean时，容器不是返回其本身，而是返回其FactoryBean#getObject方法的返回值
 *
 * @author Ivan
 * @date 2022/9/8 15:51
 */
public interface FactoryBean<T> {
    T getObject() throws Exception;

    boolean isSingleton();
}
