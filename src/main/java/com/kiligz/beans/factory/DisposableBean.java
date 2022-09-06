package com.kiligz.beans.factory;

/**
 * @author derekyi
 * @date 2020/11/29
 */
public interface DisposableBean {

	void destroy() throws Exception;
}
