package com.kiligz.beans.factory.config;

/**
 * Bean的引用
 *
 * @author Ivan
 * @date 2022/8/16 17:22
 */
public class BeanReference {

    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

}
