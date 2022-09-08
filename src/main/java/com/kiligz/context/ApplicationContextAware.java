package com.kiligz.context;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.Aware;

/**
 * 实现该接口，能感知所属ApplicationContext
 *
 * @author Ivan
 * @date 2022/9/8 09:47
 */
public interface ApplicationContextAware extends Aware {

    /**
     * 设置ApplicationContext
     */
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
