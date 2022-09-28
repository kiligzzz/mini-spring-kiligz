package com.kiligz.util;

import cn.hutool.core.util.StrUtil;

/**
 * Class工具类
 *
 * @author Ivan
 * @date 2022/9/27 10:33
 */
public class ClassUtil {
    /**
     * 是否经过cglib代理了
     */
    public static boolean isCglibProxyClass(Class<?> clazz) {
        return clazz != null && clazz.getName().contains("$$");
    }

    /**
     * 获取原始Class对象，若经过实例化cglib代理则获取其superClass为原始类，否则直接返回
     */
    public static Class<?> getOriginClass(Class<?> clazz) {
        return isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
    }

    /**
     * 获取beanClass的规范名称（类名首字母小写）
     */
    public static String getBeanNameFromClass(Class<?> clazz) {
        return StrUtil.lowerFirst(clazz.getSimpleName());
    }
}
