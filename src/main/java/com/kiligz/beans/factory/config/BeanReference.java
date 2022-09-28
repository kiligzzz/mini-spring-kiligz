package com.kiligz.beans.factory.config;

import com.kiligz.util.ClassUtil;

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

    public BeanReference(Class<?> beanClass) {
        this.beanName = ClassUtil.getBeanNameFromClass(beanClass);
    }

    public String getBeanName() {
        return beanName;
    }

}
