package com.kiligz.beans.factory;

/**
 * bean销毁的接口
 *
 * @author Ivan
 * @date 2022/9/8 15:51
 */
public interface DisposableBean {
	/**
	 * 销毁方法
	 */
	void destroy() throws Exception;
}
