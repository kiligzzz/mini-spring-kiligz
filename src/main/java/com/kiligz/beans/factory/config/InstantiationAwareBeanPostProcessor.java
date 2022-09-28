package com.kiligz.beans.factory.config;

import com.kiligz.beans.BeansException;
import com.kiligz.beans.PropertyValues;

/**
 * 实例化时感知bean接口，可生成代理对象
 *
 * @author Ivan
 * @date 2022/9/19 10:19
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * 在bean实例化之前执行（如使用pointcutAdvisor为其生成代理对象，废弃）
     */
    default Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    /**
     * 在bean实例化之后、处理pvs之前执行，对bean进行操作
     * 返回true则继续走接下来逻辑，false则直接返回（不处理pvs、设值，不放入singletonObjects）
     */
    default boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    /**
     * 在bean实例化之后，设置属性之前执行（如@Value注解，@Autowired注解）
     */
    default PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }
}
