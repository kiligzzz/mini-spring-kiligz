package com.kiligz.beans.factory.support;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.kiligz.beans.BeansException;
import com.kiligz.beans.factory.DisposableBean;
import com.kiligz.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * bean销毁的适配器
 *
 * @author Ivan
 * @date 2022/8/22 14:20
 */
public class DisposableBeanAdapter implements DisposableBean {

	private final Object bean;

	private final String beanName;

	private final String destroyMethodName;

	public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
		this.bean = bean;
		this.beanName = beanName;
		this.destroyMethodName = beanDefinition.getDestroyMethodName();
	}

	@Override
	public void destroy() throws Exception {
		if (bean instanceof DisposableBean) {
			((DisposableBean) bean).destroy();
		}

		// 避免同时继承自DisposableBean，且自定义方法与DisposableBean方法同名，销毁方法执行两次的情况
		if (StrUtil.isNotEmpty(destroyMethodName)
				&& !(bean instanceof DisposableBean && "destroy".equals(destroyMethodName))) {
			// 执行自定义方法
			Method destroyMethod = ClassUtil.getPublicMethod(bean.getClass(), destroyMethodName);
			if (destroyMethod == null) {
				throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
			}
			destroyMethod.invoke(bean);
		}
	}
}
