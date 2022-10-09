package com.kiligz.beans.factory;

/**
 * bean初始化的接口
 *
 * @author Ivan
 * @date 2022/9/8 15:51
 */
public interface InitializingBean {
	/**
	 * bean初始化方法
	 */
	void afterPropertiesSet() throws Exception;
}
