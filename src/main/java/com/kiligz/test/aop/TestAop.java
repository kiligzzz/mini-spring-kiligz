package com.kiligz.test.aop;

import com.kiligz.context.support.ClassPathXmlApplicationContext;

/**
 * @author Ivan
 * @date 2022/9/14 10:02
 */
public class TestAop {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-aop.xml");
        AopService aopService = applicationContext.getBean("aopService", AopService.class);
        aopService.printAop();
    }
}
