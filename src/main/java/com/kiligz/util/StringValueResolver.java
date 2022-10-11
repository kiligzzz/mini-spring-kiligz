package com.kiligz.util;

import com.kiligz.beans.PropertyValues;

/**
 * String值解析器
 *
 * 自动装配时解析@Value、@Autowired
 * @see com.kiligz.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor#postProcessPropertyValues(PropertyValues, Object, String)
 *
 * @author Ivan
 * @date 2022/9/26 18:16
 */
public interface StringValueResolver {
    /**
     * 解析String值
     */
    String resolveStringValue(String str);
}
