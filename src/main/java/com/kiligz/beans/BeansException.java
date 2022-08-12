package com.kiligz.beans;

/**
 * Bean异常
 *
 * @author Ivan
 * @date 2022/8/11 17:18
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
